package com.whoa.whoaserver.flowerExpression.service;

import com.whoa.whoaserver.flower.domain.Flower;
import com.whoa.whoaserver.flower.domain.FlowerImage;
import com.whoa.whoaserver.flower.repository.FlowerImageRepository;
import com.whoa.whoaserver.flower.repository.FlowerRepository;
import com.whoa.whoaserver.flower.service.S3Uploader;
import com.whoa.whoaserver.flowerExpression.domain.FlowerExpression;
import com.whoa.whoaserver.flowerExpression.dto.FlowerExpressionResponseDto;
import com.whoa.whoaserver.flowerExpression.repository.FlowerExpressionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class FlowerExpressionService {

    final FlowerExpressionRepository flowerExpressionRepository;
    final FlowerImageRepository flowerImageRepository;
    final FlowerRepository flowerRepository;
    final S3Uploader s3Uploader;

    @Transactional
    public FlowerExpressionResponseDto postFlowerExpressions(MultipartFile flowerImageUrl, Long flowerId, FlowerExpression flowerExpression) throws IOException {
        Flower flower = flowerRepository.findByFlowerId(flowerId);
        System.out.println("1여기");
        flowerExpression.setFlower(flower);
        System.out.println("2여기");
        FlowerExpression flowerExpression1 = flowerExpressionRepository.save(flowerExpression);
        System.out.println("3여기");
        String storedFileName = s3Uploader.saveFileExceptUser(flowerImageUrl, "flower");
        System.out.println("4여기");
        FlowerImage flowerImage = FlowerImage.builder()
                .flower(flower)
                .imageUrl(storedFileName)
                .flowerExpression(flowerExpression1)
                .build();
        System.out.println("5여기");
        flowerImageRepository.save(flowerImage);
        System.out.println("6여기");
        return FlowerExpressionResponseDto.of(flowerExpression1);
    }
}
