package com.czl.service.common.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.czl.config.datasource.DataSourceType;
import com.czl.config.datasource.DataSourceTypeConstant;
import com.czl.mapper.common.ComDataMapper;
import com.czl.service.common.ComDataDS1Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@SuppressWarnings({ "rawtypes" })
public class ComDataDS1ServiceImpl implements ComDataDS1Service {

	@Autowired
	private ComDataMapper comDataMapper;

	@DataSourceType(name = DataSourceTypeConstant.DS_1)
	@Override
	public List getTableData(String tableName, Map<String, Object> condition, String orderby, Map<String, Object> rowLimit) {
		log.debug("getTableData, table = [" + tableName + "], condition = " + JSONObject.toJSONString(condition));
		return comDataMapper.getTableData(tableName, condition, orderby, rowLimit);
	}

	@DataSourceType(name = DataSourceTypeConstant.DS_1)
	@Override
	public Integer getCount(String tableName, Map<String, Object> condition, String orderby, Map<String, Object> rowLimit) {
		log.debug("getCount, table = [" + tableName + "], condition = " + JSONObject.toJSONString(condition));
		return comDataMapper.getCount(tableName, condition, orderby, rowLimit);
	}

	@DataSourceType(name = DataSourceTypeConstant.DS_1)
	@Override
	@Transactional
	public Integer insertData(String tableName, Map<String, Object> insertData) {
		log.debug("insertData, table = [" + tableName + "], data = " + JSONObject.toJSONString(insertData));
		return comDataMapper.insertData(tableName, insertData);
	}

	@DataSourceType(name = DataSourceTypeConstant.DS_1)
	@Override
	@Transactional
	public Integer updateData(String tableName, Map<String, Object> mapParams, Map<String, Object> mapConditions) {
		log.debug("insertData, table = [" + tableName + "], mapParams = " + JSONObject.toJSONString(mapParams), "mapConditions=" + JSONObject.toJSONString(mapConditions));
		return comDataMapper.updateData(tableName, mapParams, mapConditions);
	}

	@DataSourceType(name = DataSourceTypeConstant.DS_1)
	@Override
	@Transactional
	public Integer deleteData(String tableName, Map<String, Object> condition) {
		log.debug("insertData, table = [" + tableName + "], condition = " + JSONObject.toJSONString(condition));
		return comDataMapper.deleteData(tableName, condition);
	}
}
