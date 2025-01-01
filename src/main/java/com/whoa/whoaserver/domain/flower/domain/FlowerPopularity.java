package com.whoa.whoaserver.domain.flower.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FlowerPopularity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	Long flowerId;

	String flowerImageUrl;

	@Column(nullable = false)
	Integer flowerRanking;

	@Column(nullable = false)
	String flowerName;

	String flowerLanguage;

	@Builder(access = AccessLevel.PRIVATE)
	public FlowerPopularity(Long flowerId, String flowerImageUrl, Integer flowerRanking, String flowerName, String flowerLanguage) {
		this.flowerId = flowerId;
		this.flowerImageUrl = flowerImageUrl;
		this.flowerRanking = flowerRanking;
		this.flowerName = flowerName;
		this.flowerLanguage = flowerLanguage;
	}

	// flowerPopularity db 처음 insert 할 때 : flowerPopularityRepsitory.count()가 0L일 때
	public static FlowerPopularity initializeFlowerPopularityRanking(Long flowerId, String flowerImageUrl, Integer flowerRanking,
																	 String flowerName, String flowerLanguage) {
		return FlowerPopularity.builder()
			.flowerId(flowerId)
			.flowerImageUrl(flowerImageUrl)
			.flowerRanking(flowerRanking)
			.flowerName(flowerName)
			.flowerLanguage(flowerLanguage)
			.build();
	}

	// 한 번 flowerPopularity insert 되면 5개의 row에 대해 계속 update를 하므로
	public void updateFlowerPopularity(Long flowerId, String flowerImageUrl, Integer flowerRanking,
									   String flowerName, String flowerLanguage) {
		this.flowerId = flowerId;
		this.flowerImageUrl = flowerImageUrl;
		this.flowerRanking = flowerRanking;
		this.flowerName = flowerName;
		this.flowerLanguage = flowerLanguage;
	}
}
