package com.adanac.demo.bootstrap.dao.picupload;

import com.adanac.demo.bootstrap.entity.picupload.AlipayDto;

public interface AlipayBaseService {
	/**
	 * 增加三方支付服务申请
	 * @param alipayDto
	 * @return
	 */
	int addAlipay(AlipayDto alipayDto);

	/**
	 * 修改三方支付服务申请
	 * @param AlipayDto
	 * @return
	 */
	int modAlipay(AlipayDto AlipayDto);

}
