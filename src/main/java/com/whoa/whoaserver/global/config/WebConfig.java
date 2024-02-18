package com.whoa.whoaserver.global.config;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;

import com.whoa.whoaserver.global.resolver.DeviceUserArgumentResolver;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final DeviceUserArgumentResolver deviceUserArgumentResolver;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(deviceUserArgumentResolver);
    }
}
