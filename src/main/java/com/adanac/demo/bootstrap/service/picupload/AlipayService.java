package com.adanac.demo.bootstrap.service.picupload;

import com.adanac.demo.bootstrap.entity.picupload.AlipayDto;

public interface AlipayService {

	/**
	 * 支付宝申请
	 * @param alipayDto
	 */
	public Boolean addAlipay(AlipayDto alipayDto);
}
