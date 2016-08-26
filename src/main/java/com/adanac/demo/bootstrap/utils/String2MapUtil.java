package com.adanac.demo.bootstrap.utils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.StringTokenizer;

public class String2MapUtil {
	/**
	 * 方法名称:transMapToString 传入参数:map 返回值:String 形如
	 * username'chenziwen^password'1234
	 */
	public static String transMapToString(Map<String, Object> map) {
		java.util.Map.Entry<String, Object> entry;
		StringBuffer sb = new StringBuffer();
		for (Iterator<Map.Entry<String, Object>> iterator = map.entrySet().iterator(); iterator.hasNext();) {
			entry = iterator.next();
			sb.append(entry.getValue().toString()).append("[")
					.append(null == entry.getKey() ? "" : entry.getKey().toString()).append("]")
					.append(iterator.hasNext() ? "," : "");
		}
		return sb.toString();
	}

	/**
	 * 方法名称:transStringToMap 传入参数:mapString 形如 username'chenziwen^password'1234
	 * 返回值:Map
	 */
	public static Map<String, Object> transStringToMap(String mapString) {
		Map<String, Object> map = new HashMap<String, Object>();
		java.util.StringTokenizer items;
		for (StringTokenizer entrys = new StringTokenizer(mapString, "^"); entrys.hasMoreTokens(); map
				.put(items.nextToken(), items.hasMoreTokens() ? ((Object) (items.nextToken())) : null))
			items = new StringTokenizer(entrys.nextToken(), "'");
		return map;
	}

	public static void main(String[] args) {
		Map<String, Object> map = new HashMap<>();
		map.put("userName", "allen");
		map.put("age", 19);
		String res = transMapToString(map);
		System.out.println(res);
	}
}
