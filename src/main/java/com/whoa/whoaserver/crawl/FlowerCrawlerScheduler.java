package com.whoa.whoaserver.crawl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.whoa.whoaserver.crawl.domain.FlowerRanking;
import com.whoa.whoaserver.crawl.service.FlowerRankingService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Component
public class FlowerCrawlerScheduler {

    @Autowired
    private final FlowerRankingService flowerRankingService;

    public FlowerCrawlerScheduler(FlowerRankingService flowerRankingService) {
        this.flowerRankingService = flowerRankingService;
    }

    @Scheduled(cron = "0 0 0 * * ?") // 매일 자정마다 실행
    public void crawlFlowerData() {
        String apiUrl = "https://flower.at.or.kr/api/returnData.api?kind=f001&serviceKey=sample&baseDate=2018-08-13&flowerGubn=1&countPerPage=999&dataType=json";

        try {
            RestTemplate restTemplate = new RestTemplate();
            String jsonResult = restTemplate.getForObject(apiUrl, String.class);

            if (jsonResult != null) {
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode rootNode = objectMapper.readTree(jsonResult);

                JsonNode itemsNode = rootNode.path("response").path("items");
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

                for (int i = 0; i < Math.min(3, flowerDataList.size()); i++) {
                    Map<String, String> flowerData = flowerDataList.get(i);
                    long flowerRankingId = i + 1;
                    String flowerName = flowerData.get("pumName");
                    String flowerPrize = flowerData.get("avgAmt");

                    flowerRankingService.saveFlowerRanking(flowerRankingId, flowerName, flowerPrize);
                }
            }else {
                System.out.println("Failed to fetch flower data from the API.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}