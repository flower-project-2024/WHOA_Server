package com.whoa.whoaserver.flower.utils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class FlowerUtils {
    public static List<String> parseFlowerEnumerationColumn(String flowerType) {

        return Arrays.stream(flowerType.split(","))
                .map(String::trim)
                .collect(Collectors.toList());
    }
}
