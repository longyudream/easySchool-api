package com.czl.base.constants;

/**
 * 系统常量类
 */
public class SystemConstants {

	/** 系统运行的标志位->调用接口需要授权验证 */
	public static final String RUN_MODE_NEED_ACCESS = "1";

	/**
	 * 系统运行的标志位->调用接口不需要授权验证
	 */
	public static final String RUN_MODE_NO_ACCESS = "0";

	/**
	 * 请求头参数 -> 用户编号（请求者用户编号）
	 */
	public static final String REQUEST_HEAD_ACCOUNT = "accountId";

	/**
	 * API需要身份验证后使用
	 */
	public static String FUNCTION_AUTH_FLAG = "1";

	/** API不需要身份验证后使用 */
	public static String FUNCTION_UNAUTH_FLAG = "0";

	/** 分页查询默认每页行数 */
	public static final Integer DEFAULT_PAGE_SIZE = 10;

	/** 分页查询默认页数 */
	public static final Integer DEFAULT_PAGE_INDEX = 1;

}
