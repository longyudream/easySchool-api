package com.czl;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

import com.czl.config.MessageListener;
import com.czl.utils.JsonSchemaUtil;
import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;

import lombok.extern.slf4j.Slf4j;

@EnableEncryptableProperties
@EnableCaching
@SpringBootApplication
@Slf4j
@MapperScan("com.czl.mapper")
public class EasyschoolAPI {
	public static void main(String[] args) {
		log.debug("======== 加载Json Schema ======== ");
		JsonSchemaUtil.loadJsonSchema();

		log.debug("======== SpringApplication 启动... ======== ");
		SpringApplication application = new SpringApplication(EasyschoolAPI.class);
		// 注册监听器，添加properties配置
		application.addListeners(new MessageListener("config/message.properties"));
		application.run(args);
	}
}
