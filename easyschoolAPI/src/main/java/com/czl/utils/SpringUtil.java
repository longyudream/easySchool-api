package com.czl.utils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import org.springframework.core.io.ClassPathResource;
import org.springframework.util.FileCopyUtils;

public class SpringUtil {

	/**
	 * 读取Jar内的配置文件。默认按UTF-8格式读取。
	 * 
	 * @param path
	 * @return
	 * @throws IOException
	 */
	public static String getClassPathResource(String path) throws IOException {
		return getClassPathResource(path, StandardCharsets.UTF_8);
	}

	/**
	 * 读取Jar内的配置文件
	 * 
	 * @param path
	 * @param charset
	 * @return
	 * @throws IOException
	 */
	public static String getClassPathResource(String path, Charset charset) throws IOException {
		ClassPathResource cpr = new ClassPathResource(path);
		byte[] bdata = FileCopyUtils.copyToByteArray(cpr.getInputStream());
		return new String(bdata, charset);
	}

}
