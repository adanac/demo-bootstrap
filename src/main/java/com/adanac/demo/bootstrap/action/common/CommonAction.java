package com.adanac.demo.bootstrap.action.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.adanac.demo.bootstrap.entity.common.BootstrapPage;
import com.adanac.demo.bootstrap.entity.common.BootstrapTable;
import com.adanac.demo.bootstrap.entity.common.CommonDto;
import com.adanac.demo.bootstrap.service.common.CommonService;
import com.adanac.demo.bootstrap.utils.ReqParamUtil;
import com.adanac.framework.log.MyLogger;
import com.adanac.framework.log.MyLoggerFactory;
import com.adanac.framework.page.Pager;
import com.adanac.framework.page.PagerParam;
import com.adanac.framework.utils.JsonUtils;
import com.adanac.framework.web.controller.BaseController;
import com.adanac.framework.web.controller.BaseResult;
import com.alibaba.fastjson.JSONObject;

@Controller
@RequestMapping("common")
public class CommonAction extends BaseController {

	public MyLogger log = MyLoggerFactory.getLogger(getClass());

	// 用于模拟缓存，提高查询效率
	private Map<String, List<String>> queryCache = new ConcurrentHashMap<String, List<String>>();

	@Autowired
	private CommonService commonService;

	/**
	 * 分页查询 返回json,无法匹配Bootstrap-table
	 */
	@ResponseBody
	@RequestMapping(value = "list")
	public String list(HttpServletRequest request, BootstrapPage page) {
		String deptCode = request.getParameter("deptCode");
		BaseResult br = new BaseResult();
		try {
			CommonDto commonDto = new CommonDto();
			commonDto.setDeptCode(deptCode);
			PagerParam param = new PagerParam();
			param.setPageSize(page.getPageSize());
			param.setPageNumber(page.getPageNumber());
			Pager<CommonDto> dataPage = commonService.queryCommonDtoPage(commonDto, param);
			br.setContent(dataPage);
			br.setStatus(BaseResult.SUCCESS);
		} catch (Exception e) {
			log.error("list====>error：", new Object[] { e });
			br.setStatus(BaseResult.ERROR);
			e.printStackTrace();
		}
		return JSONObject.toJSONString(br);
	}

	/**
	 * 返回json匹配bootstrap-table
	 * 
	 * @param request
	 * @param dto
	 * @param page
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "list2", produces = "text/html;charset=UTF-8")
	public String list2(HttpServletRequest request, CommonDto dto, BootstrapPage page) {
		BootstrapTable<CommonDto> data = new BootstrapTable<>();
		Pager<CommonDto> pager = null;
		try {
			dto.setDeptCode(ReqParamUtil.frtDeptCode2(dto.getDeptCode()));
			PagerParam param = new PagerParam();
			param.setPageSize(page.getPageSize());
			param.setPageNumber(page.getPageNumber());
			pager = commonService.queryCommonDtoPage(dto, param);
			List<CommonDto> goodsDiffList = null;
			if (pager != null && pager.getDatas() != null) {
				goodsDiffList = pager.getDatas();
				data = new BootstrapTable<CommonDto>(goodsDiffList,
						pager.getTotalDataCount() == null ? 0 : pager.getTotalDataCount(), pager.getPageNumber());
				return net.sf.json.JSONObject.fromObject(data).toString();
			} else {
				return net.sf.json.JSONObject.fromObject(new BootstrapTable<>(new ArrayList<CommonDto>(), 0, 0))
						.toString();
			}
		} catch (Exception e) {
			log.error("list error", new Object[] { e });
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 分页查询 通过response流写到页面
	 */
	@RequestMapping(value = "list3")
	public void list3(HttpServletRequest request, BootstrapPage page, HttpServletResponse response) {
		String deptCode = request.getParameter("deptCode");
		Pager<CommonDto> pager = null;
		try {
			CommonDto commonDto = new CommonDto();
			commonDto.setDeptCode(deptCode);
			PagerParam param = new PagerParam();
			param.setPageSize(page.getPageSize());
			param.setPageNumber(page.getPageNumber());
			pager = commonService.queryCommonDtoPage(commonDto, param);
		} catch (Exception e) {
			log.error("report listSupp error", new Object[] { e });
			e.printStackTrace();
		}

		BootstrapTable<CommonDto> result = new BootstrapTable<>();
		if (pager != null && null != pager.getDatas()) {
			List<CommonDto> dataList = pager.getDatas();
			result = new BootstrapTable<>(dataList, pager.getTotalDataCount() == null ? 0 : pager.getTotalDataCount(),
					pager.getPageNumber());
		}

		ajaxJson(response, JsonUtils.bean2json(result));
	}

	/**
	 * 查询部门
	 * 
	 * @param paramJson
	 * @param response
	 */
	@ResponseBody
	@RequestMapping(value = "queryDept", produces = "application/json;charset=UTF-8")
	public Map<String, Object> queryDept(Model model, HttpServletRequest request) {
		String deptCode = request.getParameter("deptCode");
		log.info("queryDept======>deptCode:" + deptCode);
		// 获取参数
		Map<String, Object> map = new HashMap<>();
		List<String> result = new ArrayList<>();
		// 獲取session中的用戶信息
		// UserInfo user = (UserInfo)
		// request.getSession().getAttribute(Const.SESSION_LOGINUSER);
		// paramMap.put("usercode", user.getUserId());
		try {
			if (queryCache.values() != null && queryCache.get(deptCode) != null) {
				result = queryCache.get(deptCode);
			} else {
				result = commonService.queryDept(deptCode);
				queryCache.put(deptCode, result);
			}
			map.put(STATUS, SUCCESS);
		} catch (Exception e) {
			log.info("queryDept========>error:", e);
			map.put(STATUS, ERROR);
		}
		// 返回
		map.put(CONTENT, result);
		return map;
	}

}
