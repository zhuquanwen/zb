package com.iscas.zb.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestData {
	public static List<Map<String,Object>> mapList = new ArrayList<Map<String,Object>>();
	static{
		for (int i = 0; i < 39; i++) {
			Map<String,Object> map1 = new HashMap<String,Object>();
			map1.put("firstCol", "字符串数据");
			map1.put("secondCol", 123);
			map1.put("thirdCol", 0.258);
			map1.put("forthCol", "这可能是个下拉框");
			map1.put("fifthCol", "XXEEGEW");
			map1.put("sixthCol", "ZEGEgg");
			mapList.add(map1);
		}

	}
}
