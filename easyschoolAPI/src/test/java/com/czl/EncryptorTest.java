package com.czl;

import org.jasypt.util.text.BasicTextEncryptor;

public class EncryptorTest {

	public static void main(String[] args) throws Exception {
		EncryptorTest encryptorTest = new EncryptorTest();
		encryptorTest.jasyptTest();
	}

	public void jasyptTest() {
		String password = "KSve/0YqtrXqx3WKPza7Ww==";
		jasyptEncode(password, "jdbc:log4jdbc:mysql://127.0.0.1:5820/paotui");
		jasyptEncode(password, "root");
		jasyptEncode(password, "root");
	}

	public void jasyptEncode(String password, String content) {
		BasicTextEncryptor encryptor = new BasicTextEncryptor();
		// application配置的jasypt.encryptor.password
		System.out.println("password=[" + password + "]");
		encryptor.setPassword(password);

		// 对root进行加密操作
		String encode = encryptor.encrypt(content);
		System.out.println("content=[" + content + "]");
		System.out.println("encode=[" + encode + "]");
		System.out.println("decode=[" + encryptor.decrypt(encode) + "]");
		System.out.println("-- ========================= --");
	}
}
