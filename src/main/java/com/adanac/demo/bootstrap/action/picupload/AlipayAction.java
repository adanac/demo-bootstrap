package com.adanac.demo.bootstrap.action.picupload;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.adanac.demo.bootstrap.entity.common.BootstrapPage;
import com.adanac.demo.bootstrap.entity.common.BootstrapTable;
import com.adanac.demo.bootstrap.entity.picupload.AlipayDto;
import com.adanac.demo.bootstrap.service.picupload.AlipayService;
import com.adanac.framework.page.Pager;
import com.adanac.framework.page.PagerParam;
import com.adanac.framework.web.controller.BaseController;
import com.alibaba.dubbo.common.json.JSON;

import net.sf.json.JSONObject;

/**
 * 图片上传(绑定支付宝)
 * 
 * @author adanac
 * @version 1.0
 */
@Controller
@RequestMapping("picupload")
public class AlipayAction extends BaseController {
	@Autowired
	private AlipayService alipayService;

	/**
	 * 跳转到支付宝列表页面
	 */
	@RequestMapping("toAlipay")
	public String toAlipay() {
		return "pages/picupload/Alipay.ftl";
	}

	@RequestMapping(value = "addAlipay2", produces = "text/html;charset=UTF-8")
	public void addAlipay2(HttpServletRequest request, AlipayDto alipayDto, HttpServletResponse response) {
		// 获取参数
		System.out.println("AlipayAction====>alipay{}" + alipayDto.toString());
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			// 调用服务
			Boolean res = alipayService.addAlipay(alipayDto);
			if (res) {
				result.put(STATUS, SUCCESS);
				result.put(MESSAGE, "支付宝申请成功");
			} else {
				result.put(STATUS, ERROR);
				result.put(MESSAGE, "支付宝申请失败");
			}
		} catch (Exception e) {
			System.out.println("AlipayAction====>addPayAlip fail");
			result.put(STATUS, ERROR);
			result.put(MESSAGE, "支付宝申请异常");
		}
		ajaxJson(response, JSONObject.fromObject(result).toString());
	}

	/**
	 * 支付宝申请
	 * 
	 * @param paramJson
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "addAlipay", produces = "text/html;charset=UTF-8")
	public void addAlipay(@RequestParam String paramJson, HttpServletRequest request, HttpServletResponse response) {
		// 获取参数
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			paramJson = java.net.URLDecoder.decode(paramJson, "utf-8");
			AlipayDto alipayDto = JSON.parse(paramJson, AlipayDto.class);
			// 调用服务
			Boolean res = alipayService.addAlipay(alipayDto);
			if (res) {
				result.put(STATUS, SUCCESS);
				result.put(MESSAGE, "支付宝申请成功");
			} else {
				result.put(STATUS, ERROR);
				result.put(MESSAGE, "支付宝申请失败");
			}
		} catch (Exception e) {
			System.out.println("addPayAlip fail");
			result.put(STATUS, ERROR);
			result.put(MESSAGE, "支付宝申请异常");
		}
		ajaxJson(response, JSONObject.fromObject(result).toString());
	}

	/**
	 * 编辑支付申请信息
	 * 
	 * @param paramJson
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "modifyAlipay", produces = "text/html;charset=UTF-8")
	public String modifyAlipay(@RequestParam String paramJson, HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> result = new HashMap<String, Object>();
		// 获取参数
		try {
			paramJson = java.net.URLDecoder.decode(paramJson, "utf-8");
			// AlipayDto AlipayDto = JSON.parse(paramJson,
			// AlipayDto.class);
			JSONObject obj = new JSONObject().fromObject(paramJson);
			AlipayDto AlipayDto = (AlipayDto) JSONObject.toBean(obj, AlipayDto.class);
			JSONObject jsonObj = JSONObject.fromObject(paramJson);
			AlipayDto.setId(Integer.parseInt((String) jsonObj.get("id")));

			// 调用服务
			Boolean res = alipayService.modifyPayAlip(AlipayDto);
			if (res) {
				result.put(STATUS, SUCCESS);
				result.put(MESSAGE, "修改支付宝申请成功");
			} else {
				result.put(STATUS, ERROR);
				result.put(MESSAGE, "修改支付宝申请失败,或PID已存在");
			}
		} catch (Exception e) {
			System.out.println("修改支付宝申请失败:" + e.toString());
		}
		return ajaxJson(response, JSONObject.fromObject(result).toString());
	}

	/**
	 * 查询支付宝列表
	 */
	@ResponseBody
	@RequestMapping(value = "ajaxList")
	public String ajaxList(HttpServletRequest request, HttpServletResponse response, AlipayDto alipay,
			BootstrapPage page) {
		try {
			PagerParam param = new PagerParam();
			param.setPageNumber(page.getPageNumber());
			param.setPageSize(page.getPageSize());
			Pager<AlipayDto> pager = alipayService.findAlipayList(alipay, param);
			List<AlipayDto> infoList = pager.getDatas();

			BootstrapTable<AlipayDto> data = new BootstrapTable<AlipayDto>(infoList,
					pager.getTotalDataCount() == null ? 0 : pager.getTotalDataCount(), pager.getPageNumber());

			return JSONObject.fromObject(data).toString();
		} catch (Exception e) {
			System.out.println("查询支付列表失败:" + e.toString());
			return "";
		}
	}

	/**
	 * 查看支付宝账号是否存在
	 * 
	 * @param paramJson
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "check", produces = "text/html;charset=UTF-8")
	public void checkAccount(HttpServletRequest request, HttpServletResponse response, String account) {
		// 获取参数
		Map<String, Object> result = new HashMap<String, Object>();
		System.out.println("checkAccount====>account:" + account);
		try {
			// 调用服务
			Boolean res = alipayService.accountExist(account);
			if (res) {
				result.put(STATUS, SUCCESS);
				result.put(MESSAGE, "该账号已被注册，支付宝申请失败");
			} else {
				result.put(STATUS, ERROR);
				result.put(MESSAGE, "该账号没有被注册");
			}
		} catch (Exception e) {
			System.out.println("查询支付宝账号是否存在失败");
			result.put(STATUS, ERROR);
			result.put(MESSAGE, "查询支付宝账号是否存在失败");
		}
		ajaxJson(response, JSONObject.fromObject(result).toString());
	}

}
