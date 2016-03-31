package com.adanac.demo.bootstrap.action.picupload;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.adanac.demo.bootstrap.action.pubapply.PubApplyAction;
import com.adanac.demo.bootstrap.entity.picupload.AlipayDto;
import com.adanac.demo.bootstrap.service.picupload.AlipayService;
import com.adanac.framework.log.MyLogger;
import com.adanac.framework.log.MyLoggerFactory;
import com.adanac.framework.web.controller.BaseController;
import com.alibaba.dubbo.common.json.JSON;

import net.sf.json.JSONObject;

/**
 * 图片上传(绑定支付宝)
 * @author adanac
 * @version 1.0
 */
@Controller
@RequestMapping(value = "/picupload", produces = "text/html;charset=UTF-8")
public class AlipayAction extends BaseController {
	@Autowired
	private AlipayService alipayService;

	private static final MyLogger logger = MyLoggerFactory.getLogger(PubApplyAction.class);

	/**
	 * 跳转到支付宝申请
	 */
	@RequestMapping(value = "/toAlipay")
	public ModelAndView toAlipay() {
		return new ModelAndView("pages/picupload/Alipay-apply-step.ftl");
	}

	/**
	 * 支付宝申请
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
			logger.error("addPayAlip fail", e);
		}
		ajaxJson(response, JSONObject.fromObject(result).toString());
	}
}
