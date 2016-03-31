package com.adanac.demo.bootstrap.dao.picupload;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adanac.demo.bootstrap.dao.common.BaseDao;
import com.adanac.demo.bootstrap.entity.constant.CodeConst;
import com.adanac.demo.bootstrap.entity.picupload.AlipayDto;
import com.adanac.framework.dac.util.DacUtils;
import com.adanac.framework.exception.SysException;
import com.adanac.framework.log.MyLogger;
import com.adanac.framework.log.MyLoggerFactory;

@Service("alipayBaseService")
public class AlipayBaseServiceImpl implements AlipayBaseService {

	@Autowired
	private BaseDao baseDao;

	private MyLogger log = MyLoggerFactory.getLogger(AlipayBaseServiceImpl.class);

	/**
	 * 增加三方支付服务申请
	 * @param paySerApp
	 * @return
	 */
	@Override
	public int addAlipay(AlipayDto alipayDto) {
		try {
			Map<String, Object> paramMap = DacUtils.convertToMap(alipayDto);
			return baseDao.execute("Alipay.addAlipay", paramMap);
		} catch (Exception e) {
			log.error("AlipayBaseServiceImpl-->addAlipay: " + alipayDto.getId() + e);
			throw new SysException(CodeConst.CODE_ERROR, e.getMessage());
		}
	}

	/**
	 * 修改三方支付服务申请
	 * @param paySerApp
	 * @return
	 */
	@Override
	public int modAlipay(AlipayDto alipayDto) {
		try {
			Map<String, Object> paramMap = DacUtils.convertToMap(alipayDto);
			return baseDao.execute("Alipay.modAlipay", paramMap);
		} catch (Exception e) {
			log.error("AlipayBaseServiceImpl-->modAlipay: " + alipayDto.getId() + e);
			throw new SysException(CodeConst.CODE_ERROR, e.getMessage());
		}
	}

}
