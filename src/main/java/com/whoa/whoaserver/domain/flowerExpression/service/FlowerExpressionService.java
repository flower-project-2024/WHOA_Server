package com.whoa.whoaserver.domain.flowerExpression.service;

import com.whoa.whoaserver.domain.flowerExpression.repository.FlowerExpressionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class FlowerExpressionService {

	private final FlowerExpressionRepository flowerExpressionRepository;

	@Transactional(readOnly = true)
	@Cacheable(cacheNames = "flowerImage", key = "#p0")
	public String getFlowerImageUrlByFlowerExpressionId(Long flowerExpressionId) {
		return flowerExpressionRepository.findByFlowerExpressionId(flowerExpressionId).getFlowerImage().getImageUrl();
	}
}
