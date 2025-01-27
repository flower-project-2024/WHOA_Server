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

	@Column(nullable = false)
	Integer rankDifference;

	@Builder(access = AccessLevel.PRIVATE)
	public FlowerPopularity(Long flowerId, String flowerImageUrl, Integer flowerRanking, String flowerName, String flowerLanguage, Integer rankDifference) {
		this.flowerId = flowerId;
		this.flowerImageUrl = flowerImageUrl;
		this.flowerRanking = flowerRanking;
		this.flowerName = flowerName;
		this.flowerLanguage = flowerLanguage;
		this.rankDifference = rankDifference;
	}

	public static FlowerPopularity initializeFlowerPopularityRanking(Long flowerId, String flowerImageUrl, Integer flowerRanking,
																	 String flowerName, String flowerLanguage, Integer rankDifference) {
		return FlowerPopularity.builder()
			.flowerId(flowerId)
			.flowerImageUrl(flowerImageUrl)
			.flowerRanking(flowerRanking)
			.flowerName(flowerName)
			.flowerLanguage(flowerLanguage)
			.rankDifference(rankDifference)
			.build();
	}

	public void updateFlowerPopularity(Long flowerId, String flowerImageUrl, Integer flowerRanking,
									   String flowerName, String flowerLanguage, Integer rankDifference) {
		this.flowerId = flowerId;
		this.flowerImageUrl = flowerImageUrl;
		this.flowerRanking = flowerRanking;
		this.flowerName = flowerName;
		this.flowerLanguage = flowerLanguage;
		this.rankDifference = rankDifference;
	}
}
