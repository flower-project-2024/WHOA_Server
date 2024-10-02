package com.whoa.whoaserver.bouquet.dto.response;

import com.whoa.whoaserver.bouquet.domain.Bouquet;
import com.whoa.whoaserver.flowerExpression.domain.FlowerExpression;
import com.whoa.whoaserver.keyword.domain.Keyword;
import com.whoa.whoaserver.mapping.domain.FlowerExpressionKeyword;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public record BouquetInfoDetailResponseV2(
	Long id,
	String purpose,
	String colorType,
	String colorName,
	String pointColor,
	String substitutionType,
	String wrappingType,
	String priceRange,
	String requirement,
	String bouquetStatus,
	List<HashMap<String, String>> selectedFlowersInfoList, // FlowerExpression id, image, language, keywords && Flower Name
	List<HashMap<String, String>> uploadedBouquetImagesInfoList


) {
	public static BouquetInfoDetailResponseV2 of(Bouquet bouquet, List<FlowerExpression> flowerExpressionList) {

		List<HashMap<String, String>> selectedFlowersInfoList = new ArrayList<>();

		for (FlowerExpression flowerExpression : flowerExpressionList) {
			HashMap<String, String> eachFlowerInfo = new HashMap<>();

			if (flowerExpression != null) {
				eachFlowerInfo.put("id", flowerExpression.getFlowerExpressionId().toString());

				eachFlowerInfo.put("flowerName", flowerExpression.getFlower().getFlowerName());

				String flowerImageUrl = (flowerExpression.getFlowerImage() != null) ? flowerExpression.getFlowerImage().getImageUrl() : "";
				eachFlowerInfo.put("flowerImageUrl", flowerImageUrl);

				eachFlowerInfo.put("flowerLanguage", flowerExpression.getFlowerLanguage());

				eachFlowerInfo.put("flowerKeywords", flowerExpression.getFlowerExpressionKeywords().stream()
					.map(FlowerExpressionKeyword::getKeyword).map(Keyword::getKeywordName)
					.collect(Collectors.joining(", ")));

			}
			selectedFlowersInfoList.add(eachFlowerInfo);
		}

		List<HashMap<String, String>> uploadedBouquetImagesInfoList = bouquet.getImages().stream()
			.map(bouquetImage -> {
				HashMap<String, String> imgHash = new HashMap<>();
				imgHash.put("bouquetImageId", bouquetImage.getId().toString());
				imgHash.put("bouquetImageUrl", bouquetImage.getFileName());
				return imgHash;
			})
			.collect(Collectors.toUnmodifiableList());


		return new BouquetInfoDetailResponseV2(
			bouquet.getId(),
			bouquet.getPurpose(),
			bouquet.getColorType().getValue(),
			bouquet.getColorName(),
			bouquet.getPointColor(),
			bouquet.getSubsitutionType().getValue(),
			bouquet.getWrappingType(),
			bouquet.getPriceRange(),
			bouquet.getRequirement(),
			bouquet.getBouquetStatus().getValue(),
			selectedFlowersInfoList,
			uploadedBouquetImagesInfoList
		);
	}
}

