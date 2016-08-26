package com.adanac.demo.bootstrap.action.common;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.adanac.demo.bootstrap.entity.common.CommonDto;
import com.adanac.demo.bootstrap.entity.constant.CodeConst;
import com.adanac.demo.bootstrap.service.common.CommonService;
import com.adanac.demo.bootstrap.service.export.ExportService;
import com.adanac.demo.bootstrap.utils.Bean2MapUtil;
import com.adanac.framework.log.MyLogger;
import com.adanac.framework.log.MyLoggerFactory;
import com.adanac.framework.web.controller.BaseController;
import com.alibaba.fastjson.JSONObject;

/**
 * 导出到Excel
 */
@Controller
@RequestMapping(value = "/export")
public class ExportController extends BaseController {

	private MyLogger logger = MyLoggerFactory.getLogger(ExportController.class);

	@Autowired
	private CommonService commonService;

	@Autowired
	private ExportService exportService;

	/**
	 * 导出查询到的信息
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/exportInfo")
	public void exportInfo(HttpServletRequest request, CommonDto commonDto, HttpServletResponse response) {
		String tableName = request.getParameter("tableName");
		// 参数封装

		// 服务请求
		List<CommonDto> dataList = commonService.queryCommonDtoList(commonDto);
		List<Map<String, Object>> list = processList(dataList);
		String basePath = request.getSession().getServletContext().getRealPath(CodeConst.PARAM_SEPARATE)
				+ CodeConst.PARAM_RES + CodeConst.PARAM_SEPARATE; // 项目物理地址
		String filepath = exportService.exportFile(basePath, tableName, list);
		downloadFile(response, filepath);
	}

	private List<Map<String, Object>> processList(List<?> list) {
		List<Map<String, Object>> res = new ArrayList<>();
		for (Object dto : list) {
			Map<String, Object> mapDto = new HashMap<String, Object>();
			mapDto = Bean2MapUtil.transBean2Map(dto);
			res.add(mapDto);
		}
		return res;
	}

	/**
	 * 下载文件
	 * 
	 * @param response
	 * @param filepath
	 */
	private void downloadFile(HttpServletResponse response, String filepath) {
		try {
			// path是指欲下载的文件的路径。
			File file = new File(filepath);
			// 取得文件名。
			String filename = file.getName();
			// 取得文件的后缀名。
			// String ext = filename.substring(filename.lastIndexOf(".") +
			// 1).toUpperCase();

			// 以流的形式下载文件。
			InputStream fis = new BufferedInputStream(new FileInputStream(filepath));
			byte[] buffer = new byte[fis.available()];
			fis.read(buffer);
			fis.close();
			// 清空response
			response.reset();
			// 设置response的Header
			response.setContentType("application/vnd.ms-excel;charset=UTF-8");
			String downLoadName = new String(filename.getBytes("utf-8"), "iso8859-1");
			response.addHeader("Content-Disposition", "attachment;filename=" + downLoadName);
			response.addHeader("Content-Length", "" + file.length());
			OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
			toClient.write(buffer);
			toClient.flush();
			toClient.close();
		} catch (IOException e) {
			logger.error("ExportAction->downloadTemp->error{}",
					new Object[] { JSONObject.toJSONString(e.getMessage()) });
		}
	}

}
