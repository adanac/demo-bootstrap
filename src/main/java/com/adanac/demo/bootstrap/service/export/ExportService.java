package com.adanac.demo.bootstrap.service.export;

import java.util.List;
import java.util.Map;

public interface ExportService {
	/**
	 * 导出文件
	 * 
	 * @param <T>
	 * @param <T>
	 * @param basePath
	 * @param param
	 * @param list
	 * @return
	 */
	public String exportFile(String basePath, String param, List<Map<String, Object>> list);

	/**
	 * 获取导出方式
	 */
	public String getExportType();
}
