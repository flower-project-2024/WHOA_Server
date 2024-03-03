package com.whoa.whoaserver.global.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "s3")
public record S3Properties(
    String region,
    String endpoint,
    String bucket,
    long presignedExpires,
    long imgMaxContentLength

) {

}
