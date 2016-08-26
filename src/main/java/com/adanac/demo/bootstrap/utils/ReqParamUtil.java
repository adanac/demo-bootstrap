package com.adanac.demo.bootstrap.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import com.adanac.framework.utils.StringUtils;

/**
 * 请求参数的处理方法类
 *
 */
public class ReqParamUtil {

	/**
	 * 格式化部门编码
	 *
	 */
	public static Map<String, String> frtDeptCode(String deptCode) {
		Map<String, String> map = new HashMap<String, String>();
		if (StringUtils.isEmpty(deptCode)) {
			return map;
		} else {
			try {
				deptCode = URLDecoder.decode(deptCode.toString(), "UTF-8");
				map.put("name", deptCode);
				String arr[] = deptCode.split(",");
				StringBuffer sb = new StringBuffer();
				for (int i = 0; i < arr.length; i++) {
					if (!StringUtils.isEmpty(arr[i])) {
						sb.append("'").append(arr[i].split("\\|")[0].trim()).append("'").append(",");
					}
				}
				map.put("code", sb.toString().substring(0, sb.toString().length() - 1));
				return map;
			} catch (UnsupportedEncodingException e) {
				return map;
			}
		}
	}

	/**
	 * 格式化部门编码
	 *
	 */
	public static String frtDeptCode2(String deptCode) {
		String res = "";
		if (StringUtils.isEmpty(deptCode)) {
			return res;
		} else {
			try {
				deptCode = URLDecoder.decode(deptCode.toString(), "UTF-8");
				String arr[] = deptCode.split(",");
				StringBuffer sb = new StringBuffer();
				for (int i = 0; i < arr.length; i++) {
					if (!StringUtils.isEmpty(arr[i])) {
						sb.append("'").append(arr[i].split("\\|")[0].trim()).append("'").append(",");
					}
				}

				return sb.toString().substring(0, sb.toString().length() - 1);
			} catch (UnsupportedEncodingException e) {
				return res;
			}
		}
	}
}
