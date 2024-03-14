package com.whoa.whoaserver.bouquet.service;

import static com.whoa.whoaserver.global.exception.ExceptionCode.IMAGE_EXTENSION_NOT_SUPPORTED;
import static com.whoa.whoaserver.global.exception.ExceptionCode.IMAGE_SIZE_LIMIT_ERROR;

import java.net.URL;
import java.time.Duration;
import java.util.function.Consumer;

import com.whoa.whoaserver.bouquet.domain.BouquetImage;
import org.springframework.stereotype.Service;

import com.whoa.whoaserver.bouquet.domain.BouquetImageRepository;
import com.whoa.whoaserver.bouquet.dto.request.PresignedUrlRequest;
import com.whoa.whoaserver.global.dto.UserContext;
import com.whoa.whoaserver.global.exception.WhoaException;
import com.whoa.whoaserver.global.properties.S3Properties;
import com.whoa.whoaserver.global.extension.Extension;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import software.amazon.awssdk.services.s3.presigner.model.PresignedPutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.model.PutObjectRequest.Builder;

@Service
@Transactional
@RequiredArgsConstructor
public class BouquetImageService {
    private final BouquetImageRepository bouquetImageRepository;
    private final S3Properties s3Properties;
    private final S3Presigner s3Presigner;

    public static final String DELIMITER = ".";

    public URL createPresignedUrl(UserContext userContext, PresignedUrlRequest request) {
        Long memberId = userContext.id();
        Long contentLength = request.contentLength();

        if (contentLength > s3Properties.imgMaxContentLength()) {
            throw new WhoaException(IMAGE_SIZE_LIMIT_ERROR);
        }

        validateExtension(request.extension());

        String fileName = generateFileName(memberId, request.imgName());
        PresignedPutObjectRequest presignedRequest = s3Presigner.presignPutObject(r -> 
            r.signatureDuration(Duration.ofSeconds(s3Properties.presignedExpires()))
                .putObjectRequest(createPutObjectRequest(contentLength, fileName)));
        
        return presignedRequest.url();
        
    }

    private void validateExtension(String extensionValue) {
        try {
            Extension extension = Extension.valueOf(extensionValue.toUpperCase());

            if (!extension.isImageType()) {
                throw new WhoaException(IMAGE_EXTENSION_NOT_SUPPORTED);
            }
        } catch (IllegalArgumentException e) {
            throw new WhoaException(IMAGE_EXTENSION_NOT_SUPPORTED);
        }
    }

    private String generateFileName(Long memberId, String imgName) {
        String s3FileName = memberId + DELIMITER + imgName;

        bouquetImageRepository.save(BouquetImage.create(s3FileName));
        return s3FileName;
    }

    private Consumer<Builder> createPutObjectRequest(long contentLength, String fileName) {
        return objectRequest -> objectRequest.bucket(s3Properties.bucket())
            .contentLength(contentLength)
            .key(fileName);
    }
    
}