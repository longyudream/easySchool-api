package com.czl.config.datasource;

import java.util.List;

import com.google.common.collect.Lists;

/**
 * 多数据源常量配置
 * 
 * @author sunyongmeng
 * @since 2020-05-12
 */
public class DataSourceTypeConstant {

	public static final String ENCRYPTOR_PREFIX = "ENC(";
	public static final String ENCRYPTOR_SURFIX = ")";

	/** 加密秘钥 */
	public static final String ENCRYPTOR_PASSWORD = "jasypt.encryptor.password";

	/** 数据库驱动名称 */
	public static final String DRIVER = "driverClassName";
	/** 数据库连接字符串 */
	public static final String URL = "url";
	/** 数据库用户名 */
	public static final String USERNAME = "username";
	/** 数据库用户密码 */
	public static final String PASSWORD = "password";

	public static final String DEFAULT_ENV_NAME = "spring.datasource.%s";
	public static final String ENV_NAME = "spring.datasource.%s.%s";

	/** 连接池中最大的活跃连接数 */
	public static final String ENV_MAX_ACTIVE = "spring.datasource.max-active";
	/** 连接池最大的空闲连接数量 */
	public static final String ENV_MAX_IDLE = "spring.datasource.max-idle";
	/** 必须保持连接的最小值 */
	public static final String ENV_MIN_IDLE = "spring.datasource.min-idle";
	/** 初始建立的连接数量 */
	public static final String ENV_INITIAL_SIZE = "spring.datasource.initial-size";

	public static final String DS_1 = "ds1";

	public static List<String> ALL_DATASOURCE = Lists.newArrayList();

	static {
		ALL_DATASOURCE.add(DS_1);
	}
}
