package com.czl.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

@Configuration
@EnableResourceServer
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

	@Override
	public void configure(HttpSecurity http) throws Exception {

		http.authorizeRequests()
				.antMatchers("/test/**").permitAll()
				.antMatchers("/**").permitAll() // 仅测试用，开放所有权限
				.anyRequest().permitAll() // 任何请求,登录后可以访问
				// 注销行为任意访问
				.and().logout().permitAll()
				.and().csrf().disable();
	}
}
