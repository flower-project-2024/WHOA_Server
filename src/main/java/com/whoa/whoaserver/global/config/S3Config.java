package com.whoa.whoaserver.global.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.whoa.whoaserver.global.exception.ExceptionCode;
import com.whoa.whoaserver.global.exception.WhoaException;
import com.whoa.whoaserver.global.extension.Extension;
import com.whoa.whoaserver.global.properties.S3Properties;

import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.services.s3.S3Configuration;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.regions.Region;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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

    @Value("${s3.bucket}")
    private String bucket;

    private final S3Properties s3Properties;

    private final AmazonS3 s3Client;

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

    public List<String> upload(List<MultipartFile> multipartFiles) {
        List<String> imgUrlList = new ArrayList<>();

        for (MultipartFile file : multipartFiles) {
            String fileName = createFileName(file.getOriginalFilename());

            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(file.getSize());
            objectMetadata.setContentType(file.getContentType());

            try(InputStream inputStream = file.getInputStream()) {
                s3Client.putObject(new PutObjectRequest(s3Properties.bucket() + "/bouquet/image", fileName, inputStream, objectMetadata)
                        .withCannedAcl(CannedAccessControlList.PublicRead));
                imgUrlList.add(s3Client.getUrl(bucket + "/bouquet/image", fileName).toString());
            } catch(IOException e) {
                throw new WhoaException(ExceptionCode.IMAGE_UPLOAD_ERROR);
            }
        }

        return imgUrlList;
    }

    private String createFileName(String fileName) {
        return UUID.randomUUID().toString().concat(getFileExtension(fileName));
    }

    private String getFileExtension(String fileName) {
        if (fileName.length() == 0) {
            throw new WhoaException(ExceptionCode.NULL_INPUT_CONTENT);
        }

        String extension = fileName.substring(fileName.lastIndexOf(".") + 1).toUpperCase();

        try {
            Extension ext = Extension.valueOf(extension);
            if (!ext.isImageType()) {
                throw new WhoaException(ExceptionCode.IMAGE_EXTENSION_NOT_SUPPORTED);
            }
        } catch (IllegalArgumentException e) {
            throw new WhoaException(ExceptionCode.IMAGE_EXTENSION_NOT_SUPPORTED);
        }

        return "." + extension.toLowerCase();
    }


}