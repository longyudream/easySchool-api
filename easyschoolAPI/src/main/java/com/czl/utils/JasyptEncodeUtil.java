package com.czl.utils;

import org.jasypt.util.text.BasicTextEncryptor;

public class JasyptEncodeUtil {

	/**
	 * 判断是否加密
	 * @param content
	 * @return
	 */
	public static boolean isEncrypt(String content) {
		if (content == null) {
			return false;
		}
		content = content.trim();
		if (content.startsWith("ENC(") && content.endsWith("")) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * jasypt解码
	 * 
	 * @param password
	 *            密码
	 * @param encryptedMessage
	 *            需解码的加密暗文
	 * @return 解码后的明文
	 */
	public static String decrypt(String password, String encryptedMessage) {
		if (encryptedMessage == null) {
			return null;
		} else {
			encryptedMessage = encryptedMessage.trim();
			if (encryptedMessage.startsWith("ENC(") && encryptedMessage.endsWith("")) {
				BasicTextEncryptor encryptor = new BasicTextEncryptor();
				encryptor.setPassword(password);
				return encryptor.decrypt(encryptedMessage);
			} else {
				return encryptedMessage;
			}
		}
	}
}
