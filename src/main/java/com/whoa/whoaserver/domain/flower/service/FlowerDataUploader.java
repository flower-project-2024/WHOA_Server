package com.whoa.whoaserver.domain.flower.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Component
@Service
public class FlowerDataUploader {

	private static final String SLASH_SEPERATOR = "/";

    private final AmazonS3 amazonS3Client;

    @Value("whoa-bucket")
    private String bucket;

    public String saveFile(MultipartFile multipartFile, String userId, String dirName) throws IOException {
        String originalFilename = multipartFile.getOriginalFilename();
        String newFilename = userId + SLASH_SEPERATOR + dirName + SLASH_SEPERATOR + UUID.randomUUID() + originalFilename;

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(multipartFile.getSize());
        metadata.setContentType(multipartFile.getContentType());

        amazonS3Client.putObject(bucket, newFilename, multipartFile.getInputStream(), metadata);
        return amazonS3Client.getUrl(bucket, newFilename).toString();
    }

    public String saveFileExceptUser(MultipartFile multipartFile, String dirName) throws IOException {
        String originalFilename = multipartFile.getOriginalFilename();
        String newFilename = dirName + SLASH_SEPERATOR + UUID.randomUUID() + originalFilename;

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(multipartFile.getSize());
        metadata.setContentType(multipartFile.getContentType());

        amazonS3Client.putObject(bucket, newFilename, multipartFile.getInputStream(), metadata);
        return amazonS3Client.getUrl(bucket, newFilename).toString();
    }
}
