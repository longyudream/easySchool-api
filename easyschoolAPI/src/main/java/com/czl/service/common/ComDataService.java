package com.czl.service.common;

import java.util.List;
import java.util.Map;

@SuppressWarnings("rawtypes")
public interface ComDataService {

	/**
	 * 查询单表数据
	 * 
	 * @param tableName
	 *            数据表名称
	 * @param condition
	 *            查询条件(仅限相等匹配)
	 * @param orderby
	 *            排序字段和方式
	 * @param rowLimit
	 *            行数限制
	 * @return
	 */
	List getTableData(String tableName, Map<String, Object> condition, String orderby, Map<String, Object> rowLimit);

	/**
	 * 查询单表数据行数
	 * 
	 * @param tableName
	 *            数据表名称
	 * @param condition
	 *            查询条件(仅限相等匹配)
	 * @param orderby
	 *            排序字段和方式
	 * @param rowLimit
	 *            行数限制
	 * @return 计数值
	 */
	Integer getCount(String tableName, Map<String, Object> condition, String orderby, Map<String, Object> rowLimit);

	/**
	 * 插入单表数据（单条数据）
	 * 
	 * @param tableName
	 *            数据表名称
	 * @param map
	 *            数据
	 * @return 处理行数
	 */
	Integer insertData(String tableName, Map<String, Object> map);

	/**
	 * 更新单表数据
	 * 
	 * @param tableName
	 *            数据表名称
	 * @param mapParams
	 *            更新内容
	 * @param mapConditions
	 *            更新条件(仅限相等匹配)
	 * @return 处理行数
	 */
	Integer updateData(String tableName, Map<String, Object> mapParams, Map<String, Object> mapConditions);

	/**
	 * 删除单表数据
	 * 
	 * @param tableName
	 *            数据表名称
	 * @param condition
	 *            删除条件(仅限相等匹配)
	 * @return 处理行数
	 */
	Integer deleteData(String tableName, Map<String, Object> condition);

}
