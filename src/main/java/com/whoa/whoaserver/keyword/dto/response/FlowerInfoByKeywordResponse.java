package com.whoa.whoaserver.keyword.dto.response;

import java.util.List;

public record FlowerInfoByKeywordResponse(
        String flowerName,
        String flowerImage,
        List<String> flowerKeyword
) {

}
