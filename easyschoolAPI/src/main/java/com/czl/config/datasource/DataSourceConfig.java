package com.czl.config.datasource;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import com.google.common.collect.Maps;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class DataSourceConfig {

	@Resource
	Environment environment;

	@Bean
	public DataSource dataSource() {
		DynamicDataSource dynamicDataSource = new DynamicDataSource();

		dynamicDataSource.setDefaultTargetDataSource(getDefaultDataSource());
		dynamicDataSource.setTargetDataSources(getDataSourceMap());

		return dynamicDataSource;
	}

	private Object getDefaultDataSource() {
		String dirver = environment.getProperty(String.format(DataSourceTypeConstant.DEFAULT_ENV_NAME, DataSourceTypeConstant.DRIVER));
		String url = environment.getProperty(String.format(DataSourceTypeConstant.DEFAULT_ENV_NAME, DataSourceTypeConstant.URL));
		String user = environment.getProperty(String.format(DataSourceTypeConstant.DEFAULT_ENV_NAME, DataSourceTypeConstant.USERNAME));
		String password = environment.getProperty(String.format(DataSourceTypeConstant.DEFAULT_ENV_NAME, DataSourceTypeConstant.PASSWORD));

		log.debug("Default DS: [URL] = " + url + ", [User]=" + user);
		DataSource dataSource = DataSourceBuilder.create().driverClassName(dirver).password(password).url(url).username(user).build();
		return dataSource;
	}

	private Map<Object, Object> getDataSourceMap() {
		Map<Object, Object> dataSourceMap = Maps.newHashMap();
		List<String> allDatasource = DataSourceTypeConstant.ALL_DATASOURCE;

		for (String name : allDatasource) {
			String dirver = environment.getProperty(String.format(DataSourceTypeConstant.ENV_NAME, name, DataSourceTypeConstant.DRIVER));
			String url = environment.getProperty(String.format(DataSourceTypeConstant.ENV_NAME, name, DataSourceTypeConstant.URL));
			String user = environment.getProperty(String.format(DataSourceTypeConstant.ENV_NAME, name, DataSourceTypeConstant.USERNAME));
			String password = environment.getProperty(String.format(DataSourceTypeConstant.ENV_NAME, name, DataSourceTypeConstant.PASSWORD));

			log.debug("Sub DS[" + name + "]: [URL] = " + url + ", [User]=" + user);

			DataSourceBuilder<?> sourceBuilder = DataSourceBuilder.create().driverClassName(dirver);
			DataSource dataSource = sourceBuilder.password(password).url(url).username(user).build();
			dataSourceMap.put(name, dataSource);
		}
		return dataSourceMap;
	}
}
