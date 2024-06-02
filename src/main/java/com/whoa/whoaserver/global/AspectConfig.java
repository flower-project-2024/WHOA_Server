package com.whoa.whoaserver.global;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AspectConfig {

    @Bean
    public ServiceExecutionTimeAspect serviceExecutionTimeAspect() {
        return new ServiceExecutionTimeAspect();
    }
}
