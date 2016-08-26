package com.adanac.demo.bootstrap.action.db;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.adanac.demo.bootstrap.service.common.QueryDbService;
import com.adanac.framework.log.MyLogger;
import com.adanac.framework.log.MyLoggerFactory;
import com.adanac.framework.utils.JsonUtils;
import com.adanac.framework.web.controller.BaseResult;
import com.alibaba.fastjson.JSONObject;

@Controller
@RequestMapping("queryDb")
public class QueryDbAction {

	public MyLogger log = MyLoggerFactory.getLogger(getClass());

	@Autowired
	private QueryDbService queryDbService;

	/**
	 * 分页查询
	 */
	@ResponseBody
	@RequestMapping(value = "getColumnNames")
	public String getColumnNames(HttpServletRequest request) {
		String tableName = request.getParameter("tableName");
		BaseResult br = new BaseResult();
		try {
			List<String> columnName = queryDbService.queryTbColumnName(tableName);
			br.setContent(columnName);
			br.setStatus(BaseResult.SUCCESS);
		} catch (Exception e) {
			log.error("getColumnNames error：", new Object[] { JsonUtils.bean2json(e) });
			br.setStatus(BaseResult.ERROR);
			e.printStackTrace();
		}

		return JSONObject.toJSONString(br);
	}
}
