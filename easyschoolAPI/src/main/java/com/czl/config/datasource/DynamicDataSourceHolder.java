package com.czl.config.datasource;

public class DynamicDataSourceHolder {
	public static final ThreadLocal<String> DATA_SOURCE_NAME = new ThreadLocal<>();

	public static void set(String name) {
		DATA_SOURCE_NAME.set(name);
	}

	public static void clear() {
		DATA_SOURCE_NAME.remove();
	}

	public static String get() {
		return DATA_SOURCE_NAME.get();
	}
}
