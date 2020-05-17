package com.czl.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.FileHeader;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;

public class ZipUtil {

	/**
	 * 对目录做Zip压缩处理
	 * @param src 需做压缩的文件路径
	 * @param dest 目标路径
	 * @param isCreateDir 压缩文件中是否生成目录结构
	 * @param password 压缩包密码（可以为空。为空时没有密码。）
	 * @return
	 * @throws ZipException
	 */
	public static String doZipFilesWithPassword(String src, String dest, boolean isCreateDir, String password)
			throws ZipException {
		File srcFile = new File(src);
		ZipParameters parameters = new ZipParameters();
		// 压缩方式
		parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
		// 压缩级别
		parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);
		// 加密方式
		if (!StringUtil.isEmpty(password)) {
			parameters.setEncryptFiles(true);
			parameters.setEncryptionMethod(Zip4jConstants.ENC_METHOD_STANDARD);
			parameters.setPassword(password);
		}

		ZipFile zipFile = new ZipFile(dest);
		if (srcFile.isDirectory()) {
			// 如果不创建目录的话,将直接把给定目录下的文件压缩到压缩文件,即没有目录结构
			if (!isCreateDir) {
				File[] subFiles = srcFile.listFiles();
				ArrayList<File> temp = new ArrayList<File>();
				Collections.addAll(temp, subFiles);
				zipFile.addFiles(temp, parameters);
				return dest;
			}
			zipFile.addFolder(srcFile, parameters);
		} else {
			zipFile.addFile(srcFile, parameters);
		}
		return dest;
	}

	/**
	 * 对文件列表做Zip压缩处理
	 * @param srcfile 源文件
	 * @param dest 目标文件（含路径）
	 * @param password 压缩包密码（可以为空。为空时没有密码。）
	 * @throws ZipException
	 */
	public static void doZipFilesWithPassword(File[] srcfile, String dest, String password) throws ZipException {
		ZipParameters parameters = new ZipParameters();
		// 压缩方式
		parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
		// 压缩级别
		parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);
		// 加密方式
		if (!StringUtil.isEmpty(password)) {
			parameters.setEncryptFiles(true);
			parameters.setEncryptionMethod(Zip4jConstants.ENC_METHOD_STANDARD);
			parameters.setPassword(password);
		}

		ZipFile zipFile = new ZipFile(dest);
		ArrayList<File> temp = new ArrayList<File>();
		Collections.addAll(temp, srcfile);
		zipFile.addFiles(temp, parameters);
	}

	/**
	 * Zip解压到文件
	 * 
	 * @param zipFileName
	 *            Zip文件名（含路径）
	 * @param password
	 *            压缩包密码（可以为空。为空时没有密码。）
	 * @param extractPath
	 *            解压路径
	 * @throws ZipException
	 */
	@SuppressWarnings("rawtypes")
	public static void doUnZipFilesWithPassword(String zipFileName, String password, String extractPath,String charset)
			throws ZipException {
		ZipFile zipFile = new ZipFile(zipFileName);
		// 设置文件编码
		if (charset == null) {
			zipFile.setFileNameCharset("GBK");
		}
		zipFile.setFileNameCharset(charset);
		if (zipFile.isEncrypted()) {
			zipFile.setPassword(password);
		}
		List fileHeaderList = zipFile.getFileHeaders();
		for (int i = 0; i < fileHeaderList.size(); i++) {
			FileHeader fileHeader = (FileHeader) fileHeaderList.get(i);
			zipFile.extractFile(fileHeader, extractPath);
		}
	}
	
	public static String doZipFolder(String src, String dest, boolean isCreateDir, String password)
			throws ZipException {
		File srcFile = new File(src);
		ZipParameters parameters = new ZipParameters();
		// 压缩方式
		parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
		// 压缩级别
		parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);
		// 加密方式
		if (!StringUtil.isEmpty(password)) {
			parameters.setEncryptFiles(true);
			parameters.setEncryptionMethod(Zip4jConstants.ENC_METHOD_STANDARD);
			parameters.setPassword(password);
		}

		ZipFile zipFile = new ZipFile(dest);
		if (srcFile.isDirectory()) {
			// 如果不创建目录的话,将直接把给定目录下的文件压缩到压缩文件,即没有目录结构
			if (!isCreateDir) {
				File[] subFiles = srcFile.listFiles();
				for (int i = 0; i < subFiles.length; i++) {
					if(subFiles[i].isDirectory()){
						
					}
				}
				ArrayList<File> temp = new ArrayList<File>();
				Collections.addAll(temp, subFiles);
				zipFile.addFiles(temp, parameters);
				return dest;
			}
			File[] subFiles = srcFile.listFiles();
			for (int i = 0; i < subFiles.length; i++) {
				zipFile.addFolder(subFiles[i], parameters);
			}
		} else {
			zipFile.addFile(srcFile, parameters);
		}
		return dest;
	}
	
}
