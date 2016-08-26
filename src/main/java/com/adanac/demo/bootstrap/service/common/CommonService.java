package com.adanac.demo.bootstrap.service.common;

import java.util.List;

import com.adanac.demo.bootstrap.entity.common.CommonDto;
import com.adanac.framework.page.Pager;
import com.adanac.framework.page.PagerParam;
import com.adanac.framework.web.controller.BaseResult;

public interface CommonService {

	/**
	 * 分页查询dto
	 */
	Pager<CommonDto> queryCommonDtoPage(CommonDto commonDto, PagerParam param);

	/**
	 * 查询dto列表
	 */
	List<CommonDto> queryCommonDtoList(CommonDto commonDto);

	/**
	 * 保存单个用户
	 * 
	 * @param commonDto
	 * @return
	 */
	BaseResult addCommonDto(CommonDto commonDto);

	/**
	 * 批量保存用户
	 * 
	 * @param paramJson
	 * @return
	 */
	BaseResult addCommonDto(List<CommonDto> commonDtoList);

	BaseResult getCommonDtoByID(String id);

	CommonDto getCommonDto(String id);

	BaseResult modCommonDto(String id);

	BaseResult delCommonDto(String id);

	List<String> queryDept(String deptCode);

}
