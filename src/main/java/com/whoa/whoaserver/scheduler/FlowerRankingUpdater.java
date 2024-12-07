package com.whoa.whoaserver.scheduler;

import com.whoa.whoaserver.domain.flower.domain.Flower;
import com.whoa.whoaserver.domain.flower.domain.FlowerImage;
import com.whoa.whoaserver.domain.flower.domain.FlowerRanking;
import com.whoa.whoaserver.domain.flower.repository.FlowerRepository;
import com.whoa.whoaserver.domain.flower.repository.ranking.FlowerRankingRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Component
@RequiredArgsConstructor
public class FlowerRankingUpdater {

	private final EntityManager entityManager;
	private final FlowerRankingRepository flowerRankingRepository;
	private final FlowerRepository flowerRepository;

	@Transactional
	public void updateFlowerRanking(final Long flowerRankingId, final String pumName, final String avgAmt, final String date) {
		FlowerRanking flowerRanking = flowerRankingRepository.findByFlowerRankingId(flowerRankingId);
		Flower findFlower = flowerRepository.findByFlowerName(pumName);
		if (findFlower == null) {
			flowerRanking.update(pumName, null, avgAmt, date, null, null);
		} else {
			String findFlowerLanguage = findFlower.getFlowerExpressions().get(0).getFlowerLanguage();
			String flowerImageUrl = null;
			for (FlowerImage flowerImage : findFlower.getFlowerImages()) {
				flowerImageUrl = flowerImage.getImageUrl();
				if (flowerImageUrl != null)
					break;
			}
			Long findFlowerId = findFlower.getFlowerId();
			flowerRanking.update(pumName, findFlowerLanguage, avgAmt, date, flowerImageUrl, findFlowerId);
		}
		flowerRankingRepository.save(flowerRanking);
	}

	@Transactional
	public void updateOnlyFlowerRankingDateToFormattedDate(String formattedDate) {
		flowerRankingRepository.updateAllFlowerRankingDates(formattedDate);
		entityManager.clear();
	}
}
