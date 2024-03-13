package com.whoa.whoaserver;

import com.whoa.whoaserver.crawl.service.FlowerCrawlerScheduler;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(properties = {"crawl.service-key="})
public class FlowerCrawlerSchedulerTest {

    @Autowired
    private FlowerCrawlerScheduler flowerCrawlerScheduler;

    @Test
    public void testCrawlFlowerData() {

        flowerCrawlerScheduler.crawlFlowerData();

    }
}
