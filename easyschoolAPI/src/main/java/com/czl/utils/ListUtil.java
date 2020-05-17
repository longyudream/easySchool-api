package com.czl.utils;

import java.util.List;

/**
 * 集合 - List工具类
 * 
 * @author qmy
 * @since 2019/12/27 10:14
 */
@SuppressWarnings("rawtypes")
public class ListUtil {

	public static boolean isEmpty(List list) {
		if (list == null || list.size() == 0) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean isNotEmpty(List list) {
		return !isEmpty(list);
	}

}
