package com.whoa.whoaserver.domain.bouquet.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.whoa.whoaserver.domain.bouquet.domain.type.BouquetStatus;
import com.whoa.whoaserver.domain.bouquet.domain.type.ColorTypeOption;
import com.whoa.whoaserver.domain.bouquet.domain.type.FlowerSubstitutionTypeOption;
import com.whoa.whoaserver.global.common.BaseEntity;
import com.whoa.whoaserver.domain.member.domain.Member;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "bouquet")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Bouquet extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "bouquet_id")
	private Long id;

	@JsonManagedReference
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id", nullable = false)
	private Member member;

	@Column(nullable = true)
	private String bouquetName;

	@Column(nullable = false)
	private String purpose;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private ColorTypeOption colorType;

	@Column(nullable = false)
	private String colorName;

	private String pointColor;

	@Column(nullable = false)
	private String flowerType;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private FlowerSubstitutionTypeOption subsitutionType;

	private String wrappingType;

	@Column(nullable = false)
	private String priceRange;

	private String requirement;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private BouquetStatus bouquetStatus;

	@JsonManagedReference("bouquet-image")
	@OneToMany(mappedBy = "bouquet", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<BouquetImage> images = new ArrayList<>();

	private String realImageUrl;


	@Builder(access = AccessLevel.PRIVATE)
	public Bouquet(Member member, String bouquetName, String purpose, ColorTypeOption colorType, String colorName,
				   String pointColor, String flowerType, FlowerSubstitutionTypeOption subsitutionType, String wrappingType,
				   String priceRange, String requirement, BouquetStatus bouquetStatus) {
		this.member = member;
		this.bouquetName = bouquetName;
		this.purpose = purpose;
		this.colorType = colorType;
		this.colorName = colorName;
		this.pointColor = pointColor;
		this.flowerType = flowerType;
		this.subsitutionType = subsitutionType;
		this.wrappingType = wrappingType;
		this.priceRange = priceRange;
		this.requirement = requirement;
		this.bouquetStatus = bouquetStatus;
	}

	public static Bouquet orderBouquet(Member member, String bouquetName, String purpose, ColorTypeOption colorType,
									   String colorName, String pointColor, String flowerType, FlowerSubstitutionTypeOption subsitutionType,
									   String wrappingType, String priceRange, String requirement) {
		return Bouquet.builder()
			.member(member)
			.bouquetName(bouquetName)
			.purpose(purpose)
			.colorType(colorType)
			.colorName(colorName)
			.pointColor(pointColor)
			.flowerType(flowerType)
			.subsitutionType(subsitutionType)
			.wrappingType(wrappingType)
			.priceRange(priceRange)
			.requirement(requirement)
			.bouquetStatus(BouquetStatus.INCOMPLETED)
			.build();

	}

	public void changeBouquet(String bouquetName, String purpose, ColorTypeOption colorType, String colorName, String pointColor,
							  String flowerType, FlowerSubstitutionTypeOption subsitutionType, String wrappingType,
							  String priceRange, String requirement) {
		this.bouquetName = bouquetName;
		this.purpose = purpose;
		this.colorType = colorType;
		this.colorName = colorName;
		this.pointColor = pointColor;
		this.flowerType = flowerType;
		this.subsitutionType = subsitutionType;
		this.wrappingType = wrappingType;
		this.priceRange = priceRange;
		this.requirement = requirement;
	}

	public void initializeBouquetWrappingType(String wrappingType) {
		this.wrappingType = wrappingType;
	}

	public void updateBouquetStatus(BouquetStatus bouquetStatus) {
		this.bouquetStatus = bouquetStatus;
	}

	public void registerRealBouquetImagePath(String realImageUrl) {
		this.realImageUrl = realImageUrl;
	}

	public void updateBouquetName(String bouquetName) {
		this.bouquetName = bouquetName;
	}
}
