package com.whoa.whoaserver.crawl.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Component
public class FlowerCrawlerScheduler {

    @Value("${crawl.service-key}")
    private String serviceKey;

    private final FlowerRankingService flowerRankingService;

    @Autowired
    public FlowerCrawlerScheduler(@Value("${crawl.service-key}") String serviceKey, FlowerRankingService flowerRankingService) {
        this.serviceKey = serviceKey;
        this.flowerRankingService = flowerRankingService;
    }

    @Scheduled(cron = "0 0 0 * * ?")
    public void crawlFlowerData() {
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDate = currentDate.format(formatter);
        String apiUrl = "https://flower.at.or.kr/api/returnData.api?kind=f001&serviceKey="+serviceKey+"&baseDate=" + formattedDate + "&flowerGubn=1&dataType=json";

        try {
            RestTemplate restTemplate = new RestTemplate();
            String jsonResult = restTemplate.getForObject(apiUrl, String.class);

            if (jsonResult != null) {
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode rootNode = objectMapper.readTree(jsonResult);

                JsonNode responseNode = rootNode.path("response");
                String resultMsg = responseNode.path("resultMsg").asText();
                int numOfRows = responseNode.path("numOfRows").asInt();

                if ("OK".equals(resultMsg) && numOfRows >= 3) {
                    JsonNode itemsNode = responseNode.path("items");
                    Iterator<JsonNode> items = itemsNode.elements();

                    List<Map<String, String>> flowerDataList = new ArrayList<>();

                    while (items.hasNext()) {
                        JsonNode item = items.next();
                        Map<String, String> flowerData = new HashMap<>();

                        flowerData.put("pumName", item.path("pumName").asText());
                        flowerData.put("avgAmt", item.path("avgAmt").asText());

                        flowerDataList.add(flowerData);
                    }

                    flowerDataList.sort(Comparator.comparingInt(data -> Integer.parseInt(data.get("avgAmt"))));

                    Set<String> savedNames = new HashSet<>();
                    long flowerRankingId = 0;
                    for (int i = 0; i < flowerDataList.size(); i++) {
                        Map<String, String> flowerData = flowerDataList.get(i);
                        String flowerName = flowerData.get("pumName");
                        String flowerPrice = flowerData.get("avgAmt");

                        if (!savedNames.contains(flowerName)) {
                            savedNames.add(flowerName);
                            flowerRankingId++;
                            flowerRankingService.saveFlowerRanking(flowerRankingId, flowerName, flowerPrice, formattedDate);
                        }
                        if (flowerRankingId==3)
                            break;
                    }
                } else {
                    System.out.println("Failed to fetch flower data from the API.");
                }

            }

        } catch (JsonMappingException e) {
            throw new RuntimeException(e);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}