package com.htl.microservice.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

public class DateUtils {
	
	public static void main(String[] args) {
		System.out.println(addSeconds("2021-05-06 19:38:00", 60));
		System.out.println(formatStrUTCToDateTimestamp("2021-05-06T19:38:00Z"));
		System.out.println(formatStrDateTimestamp("2021-05-06 19:38:00"));
		List<Map<String, Object>> list = new ArrayList<>();
		for(int i=0; i<100; i++) {
			Map<String, Object> map = new HashMap<>();
			map.put("time", "2021-05-06T00:00:30Z");
			map.put("name", new Integer(i));
			list.add(map);
		}
		System.out.println(getClosedMap("2021-05-06 08:00:00", 30, list));
		System.out.println(getClosedMap("2021-05-06 08:01:00", 30, list));
	}
	
	public static String addSeconds(String dateStr, Integer seconds) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		try {
			Date date = sdf.parse(dateStr);
			return sdf.format(new Date((date.getTime()/1000 + seconds)*1000));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static Map<String, Object> getClosedMap(String time, Integer radius, List<Map<String, Object>> list){
		Integer timestamp = formatStrDateTimestamp(time);
		if(timestamp == null || list == null || list.size() == 0) {
			return null;
		}
		Integer abs = null;
		Map<String, Object> rmap = null;
		for(Map<String, Object> map: list) {
			if(map.get("time") == null)continue;
			int sub = Math.abs(formatStrUTCToDateTimestamp(map.get("time").toString()) - timestamp);
			if((abs == null && sub <= radius) || (sub <= radius && sub < abs)) {
				rmap = map;
				abs = sub;
			}
		}
		
		return rmap;
	}
	
	public static Integer formatStrDateTimestamp(String time) {
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			return (int) (sf.parse(time).getTime()/1000);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Integer formatStrUTCToDateTimestamp(String utcTime) {
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US);
		TimeZone utcZone = TimeZone.getTimeZone("UTC");
		sf.setTimeZone(utcZone);
		try {
			return (int) (sf.parse(utcTime).getTime()/1000);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
}
