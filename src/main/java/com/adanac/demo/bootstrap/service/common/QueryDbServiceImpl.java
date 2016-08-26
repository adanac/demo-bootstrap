package com.adanac.demo.bootstrap.service.common;

import java.util.List;

import org.springframework.stereotype.Service;

import com.adanac.demo.bootstrap.utils.db.DbTableUtil;

@Service
public class QueryDbServiceImpl implements QueryDbService {

	@Override
	public List<String> queryTbColumnName(String tableName) {
		List<String> columnNames = DbTableUtil.getColNamesByTableName(tableName);
		return columnNames;
	}

}
