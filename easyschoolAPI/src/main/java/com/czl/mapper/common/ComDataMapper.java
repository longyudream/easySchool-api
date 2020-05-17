package com.czl.mapper.common;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

/**
 * dao层接口
 * 
 * @author czl
 * @since 2018-09-21
 */
@SuppressWarnings("rawtypes")
public interface ComDataMapper {
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
	List getTableData(@Param("tableName") String tableName, @Param("params") Map<String, Object> condition,
			@Param("orderby") String orderby, @Param("limit") Map rowLimit);

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
	Integer getCount(@Param("tableName") String tableName, @Param("params") Map<String, Object> condition,
			@Param("orderby") String orderby, @Param("limit") Map rowLimit);

	/**
	 * 插入单表数据（单条数据）
	 * 
	 * @param tableName
	 *            数据表名称
	 * @param map
	 *            数据
	 * @return 处理行数
	 */
	Integer insertData(@Param("tableName") String tableName, @Param("params") Map<String, Object> map);

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
	Integer updateData(@Param("tableName") String tableName, @Param("params") Map<String, Object> mapParams,
			@Param("conditions") Map<String, Object> mapConditions);

	/**
	 * 删除单表数据
	 * 
	 * @param tableName
	 *            数据表名称
	 * @param condition
	 *            删除条件(仅限相等匹配)
	 * @return 处理行数
	 */
	Integer deleteData(@Param("tableName") String tableName, @Param("params") Map<String, Object> condition);

}