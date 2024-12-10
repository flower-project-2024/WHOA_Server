package com.whoa.whoaserver.domain.flower.utils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class FlowerUtils {

	private static final String COMMA_SEPARATOR = ",";
    public static List<String> parseFlowerEnumerationColumn(String flowerType) {

        return Arrays.stream(flowerType.split(COMMA_SEPARATOR))
                .map(String::trim)
                .collect(Collectors.toList());
    }
}
