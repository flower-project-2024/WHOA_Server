package com.whoa.whoaserver.domain.keyword.service;

import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class PaletteColors {
	private static final Map<String, String> paletteColorMap = new HashMap<>();
	private static final Map<String, List<String>> colorPaletteMap = new HashMap<>();

	static { // 기본 색감 -> 연한 색감 -> 진한 색감 순서
		paletteColorMap.put("#F3142C", "Red");
		paletteColorMap.put("#FFC6C6", "Red");
		paletteColorMap.put("#AF0000", "Red");

		paletteColorMap.put("#FF8A49", "Orange");
		paletteColorMap.put("#FFD3AB", "Orange");
		paletteColorMap.put("#88330A", "Orange");

		paletteColorMap.put("#FFEA2C", "Yellow");
		paletteColorMap.put("#FFF4B8", "Yellow");
		paletteColorMap.put("#8F6700", "Yellow");

		paletteColorMap.put("#ABE472", "Green");
		paletteColorMap.put("#E0F3CD", "Green");
		paletteColorMap.put("#0C550F", "Green");

		paletteColorMap.put("#5681EE", "Blue");
		paletteColorMap.put("#B5D3F4", "Blue");
		paletteColorMap.put("#254495", "Blue");

		paletteColorMap.put("#FF8AD0", "Pink");
		paletteColorMap.put("#FFD5EC", "Pink");
		paletteColorMap.put("#86007C", "Pink");

		paletteColorMap.put("#AD65F4", "Purple");
		paletteColorMap.put("#D5C5F8", "Purple");
		paletteColorMap.put("#5000A1", "Purple");

		paletteColorMap.put("#FDFFF8", "Neutral");
		paletteColorMap.put("#E3E3E1", "Neutral");
		paletteColorMap.put("#251F1F", "Neutral");
	}

	static {
		colorPaletteMap.put("Red", List.of("#F3142C", "#FFC6C6", "#AF0000"));
		colorPaletteMap.put("Orange", List.of("#FF8A49", "#FFD3AB", "#88330A"));
		colorPaletteMap.put("Yellow", List.of("#FFEA2C", "#FFF4B8", "#8F6700"));
		colorPaletteMap.put("Green", List.of("#ABE472", "#E0F3CD", "#0C550F"));
		colorPaletteMap.put("Blue", List.of("#5681EE", "#B5D3F4", "#254495"));
		colorPaletteMap.put("Pink", List.of("#FF8AD0", "#FFD5EC", "#86007C"));
		colorPaletteMap.put("Purple", List.of("#AD65F4", "#D5C5F8", "#5000A1"));
		colorPaletteMap.put("Neutral", List.of("#FDFFF8", "#E3E3E1", "#251F1F"));
	}

	public static Map<String, String> getPaletteColorMap() {
		return paletteColorMap;
	}
	public static Map<String, List<String>> getColorPaletteMap() {
		return colorPaletteMap;
	}

}
