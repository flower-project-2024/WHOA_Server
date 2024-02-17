package com.whoa.whoaserver.global.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.whoa.whoaserver.global.properties.S3Properties;

import lombok.RequiredArgsConstructor;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.services.s3.S3Configuration;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.regions.Region;

import java.net.URI;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(S3Properties.class)
public class S3Config {
    @Value("${aws.access-key}")
    private String accessKey;

    @Value("${aws.secret-key}")
    private String secretKey;

    @Value("ap-northeast-2")
    private String region;

    private final S3Properties s3Properties;

    @Bean
    public AmazonS3 amazonS3Client() {
        AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);

        return AmazonS3ClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(region)
                .build();
    }

    @Bean
    public S3Presigner s3Presigner(AwsCredentialsProvider credentialsProvider, S3Configuration s3Configuration) {
        return S3Presigner.builder()
                .region(Region.of(s3Properties.region()))
                .endpointOverride(URI.create(s3Properties.endpoint()))
                .credentialsProvider(credentialsProvider)
                .serviceConfiguration(s3Configuration)
                .build();
    }

    @Bean
    public AwsCredentialsProvider prodAwsCredentialsProvider() {
        return DefaultCredentialsProvider.create();
    }

    @Bean
    public S3Configuration prodS3Configuration() {
        return S3Configuration.builder()
            .build();
    }

}