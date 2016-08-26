package com.adanac.demo.bootstrap.service.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adanac.demo.bootstrap.dao.common.BaseDao;
import com.adanac.demo.bootstrap.entity.common.CommonDto;
import com.adanac.demo.bootstrap.entity.constant.CodeConst;
import com.adanac.framework.log.MyLogger;
import com.adanac.framework.log.MyLoggerFactory;
import com.adanac.framework.page.Pager;
import com.adanac.framework.page.PagerParam;
import com.adanac.framework.web.controller.BaseResult;

@Service
public class CommonServiceImpl implements CommonService {

	@Autowired
	BaseDao baseDao;

	private MyLogger log = MyLoggerFactory.getLogger(CommonServiceImpl.class);

	@Override
	public Pager<CommonDto> queryCommonDtoPage(CommonDto commonDto, PagerParam param) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", commonDto.getId() == null ? "" : commonDto.getId());
		paramMap.put("deptCode", commonDto.getDeptCode() == null ? "" : commonDto.getDeptCode());
		paramMap.put("username", commonDto.getUserName() == null ? "" : "%" + commonDto.getUserName() + "%");
		return baseDao.queryForPage("tabCommon.queryCond", paramMap, CommonDto.class, param.getPageSize(),
				param.getPageNumber());
	}

	@Override
	public List<CommonDto> queryCommonDtoList(CommonDto commonDto) {
		log.info("====queryCommonDtoList===={commonDto:" + commonDto.toString() + "}");
		try {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("deptCode", commonDto.getDeptCode());
			paramMap.put("username", commonDto.getUserName());
			paramMap.put("passwd", commonDto.getPasswd());
			paramMap.put("age", commonDto.getAge());
			List<CommonDto> commonDtoList = baseDao.queryForList("tabCommon.select", paramMap, CommonDto.class);
			return commonDtoList;
		} catch (Exception e) {
			log.error("====queryCommonDtoList====error:" + e.getMessage());
		}
		return null;
	}

	@Override
	public BaseResult addCommonDto(CommonDto commonDto) {
		log.info("===addCommonDto==={commonDto:" + commonDto.toString() + "}");
		BaseResult br = new BaseResult();
		try {
			int count = baseDao.execute("tabCommon.insert", commonDto);
			br.setContent(count);
			br.setStatus(BaseResult.SUCCESS);
		} catch (Exception e) {
			log.error("addCommonDto Error:" + e.getMessage());
			e.printStackTrace();
			br.setContent("addCommonDto error");
			br.setStatus(BaseResult.ERROR);
		}
		return br;
	}

	@SuppressWarnings("unchecked")
	@Override
	public BaseResult addCommonDto(List<CommonDto> commonDtoList) {
		log.info("===addCommonDto==={commonDtoList:" + commonDtoList.size() + "}");
		BaseResult br = new BaseResult();
		Map<String, Object>[] maps = new HashMap[commonDtoList.size()];
		try {
			int index = 0;
			for (CommonDto dto : commonDtoList) {
				Map<String, Object> map = dto.toMap(); // 封装map数据
				maps[index++] = map;
			}
			int[] res = baseDao.batchUpdate("tabCommon.insert", maps);
			br.setContent(res);
			br.setStatus(BaseResult.SUCCESS);
		} catch (Exception e) {
			log.error("addCommonDto Error:" + e.getMessage());
			e.printStackTrace();
			br.setContent("addCommonDto error");
			br.setStatus(BaseResult.ERROR);
		}
		return br;
	}

	@Override
	public BaseResult getCommonDtoByID(String id) {
		log.info("====getCommonDtoByID===={id:" + id + "}");
		BaseResult br = new BaseResult();
		try {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("id", id);
			log.info("getFromDB {} ", paramMap);
			CommonDto commonDto = baseDao.queryForObject("tabCommon.select", paramMap, CommonDto.class);
			br.setContent(commonDto);
			br.setStatus(BaseResult.SUCCESS);
		} catch (Exception e) {
			log.info("====getCommonDtoByID====error:" + e.getMessage());
			br.setStatus(BaseResult.ERROR);
		}
		return br;
	}

	@Override
	public BaseResult modCommonDto(String id) {
		BaseResult br = new BaseResult();
		try {
			log.info("====modCommonDto===={id:" + id + "}");
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("id", id);
			CommonDto commonDto = baseDao.queryForObject("tabCommon.select", paramMap, CommonDto.class);
			if (commonDto != null) {
				int count = baseDao.execute("tabCommon.update", commonDto);
				br.setContent(count);
				br.setStatus(count > 0 ? BaseResult.SUCCESS : BaseResult.ERROR);
			}
		} catch (Exception e) {
			log.error("====modCommonDto====error:" + e.getMessage());
			br.setStatus(BaseResult.ERROR);
		}
		return br;
	}

	@Override
	public BaseResult delCommonDto(String id) {
		log.info("====delCommonDto===={id:" + id + "}");
		BaseResult br = new BaseResult();
		try {
			Map<String, Object> pmap = new HashMap<String, Object>();
			pmap.put("id", id);
			int res = baseDao.execute("tabCommon.delete", pmap);
			br.setContent(res);
			br.setStatus(BaseResult.SUCCESS);
		} catch (Exception e) {
			log.error("====delCommonDto====error:" + e.getMessage());
			br.setStatus(BaseResult.ERROR);
		}
		return br;
	}

	@Override
	public CommonDto getCommonDto(String id) {
		log.info("====getCommonDto===={id:" + id + "}");
		try {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("id", id);
			log.info("getFromDB {} ", paramMap);
			CommonDto commonDto = baseDao.queryForObject("tabCommon.select", paramMap, CommonDto.class);
			return commonDto;
		} catch (Exception e) {
			log.error("调用getCommonDto异常,error{}",
					new Object[] { "[" + com.alibaba.fastjson.JSONObject.toJSONString(id) + "]", e.getMessage() });
		}
		return null;
	}

	@Override
	public List<String> queryDept(String deptCode) {
		List<String> list = new ArrayList<>();
		PagerParam pagerParam = new PagerParam();
		pagerParam.setPageNumber(CodeConst.NUM_1);
		pagerParam.setPageSize(CodeConst.NUM_10);
		deptCode = deptCode == null ? "" : deptCode;
		Pager<CommonDto> dataList = findDeptByCode(deptCode, pagerParam);
		if (dataList != null && dataList.getDatas().size() > 0) {
			for (CommonDto dto : dataList.getDatas()) {
				list.add(dto.getDeptCode() + "|" + dto.getUserName());
			}
		}
		return list;
	}

	public Pager<CommonDto> findDeptByCode(String deptCode, PagerParam pageParam) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("deptCode", deptCode);
		return baseDao.queryForPage("tabCommon.findDeptByCode", map, CommonDto.class, pageParam.getPageSize(),
				pageParam.getPageNumber());
	}

}
