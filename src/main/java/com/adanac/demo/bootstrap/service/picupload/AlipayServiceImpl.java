package com.adanac.demo.bootstrap.service.picupload;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adanac.demo.bootstrap.dao.picupload.AlipayBaseService;
import com.adanac.demo.bootstrap.entity.picupload.AlipayDto;

@Service
public class AlipayServiceImpl implements AlipayService {

	@Autowired
	private AlipayBaseService alipayBaseService;

	@Override
	public Boolean addAlipay(AlipayDto alipayDto) {
		int flag = alipayBaseService.addAlipay(alipayDto);
		if (flag > 0) {
			return true;
		} else {
			return false;
		}
	}

}
