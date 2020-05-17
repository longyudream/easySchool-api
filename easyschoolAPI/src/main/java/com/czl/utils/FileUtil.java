package com.czl.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;

import sun.misc.BASE64Encoder;

public class FileUtil {

	public static void fileCompare(String fileName1, String fileName2) {
		File f1 = new File(fileName1);
		File f2 = new File(fileName2);
		try {

			FileReader in11 = new FileReader(f1);
			FileReader in22 = new FileReader(f2);
			BufferedReader in1 = new BufferedReader(in11);
			BufferedReader in2 = new BufferedReader(in22);
			int n1 = 0, n2 = 0;
			int line = 1;
			n2 = in2.read();
			n1 = in1.read();
			int max = (n1 > n2 ? n1 : n2);
			while (max > -1) {
				line++;
				String s1, s2;
				s1 = in1.readLine();
				s2 = in2.readLine();
				if (!s1.equals(s2)) {
					System.out.println("line:" + line);
					System.out.println("File1:" + s1);
					System.out.println("File2:" + s2);
				}
				max--;
			}
			in22.close();
			in11.close();
			in2.close();
			in1.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String readTxtFile(String encoding, String fileName) {
		if (encoding == null) {
			encoding = "UTF-8";
		}

		StringBuffer sb = new StringBuffer();
		try {
			File file = new File(fileName);
			if (file.isFile() && file.exists()) { // 判断文件是否存在
				InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding);// 考虑到编码格式
				BufferedReader bufferedReader = new BufferedReader(read);
				String lineTxt = null;
				while ((lineTxt = bufferedReader.readLine()) != null) {
					// System.out.println(lineTxt);
					sb.append(lineTxt).append("\n");
				}
				read.close();
				return sb.toString();
			} else {
				System.out.println("找不到指定的文件");
				return null;
			}
		} catch (Exception e) {
			System.out.println("读取文件内容出错");
			e.printStackTrace();
			return null;
		}
	}

	public static String readTxtFileLine(String encoding, String fileName, int line) {
		if (encoding == null) {
			encoding = "UTF-8";
		}

		StringBuffer sb = new StringBuffer();
		int lineCount = 1;
		try {
			File file = new File(fileName);
			if (file.isFile() && file.exists()) { // 判断文件是否存在
				InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding);// 考虑到编码格式
				BufferedReader bufferedReader = new BufferedReader(read);
				String lineTxt = null;
				while ((lineTxt = bufferedReader.readLine()) != null) {
					// System.out.println(lineTxt);
					if (lineCount == line) {
						sb.append(lineTxt);
						break;
					}
					lineCount++;
				}
				read.close();
				return sb.toString();
			} else {
				System.out.println("找不到指定的文件");
				return null;
			}
		} catch (Exception e) {
			System.out.println("读取文件内容出错");
			e.printStackTrace();
			return null;
		}
	}

	public static boolean copyFile(String oldFileName, String newPath, String newFileName) throws Exception {
		try {
			int byteread = 0;
			File newFilePath = new File(newPath);
			newFilePath.mkdirs();
			File oldfile = new File(oldFileName);

			newFileName = newPath + newFileName;
			if (!oldFileName.equals(newFileName)) {
				if (oldfile.exists()) {
					InputStream inStream = new FileInputStream(oldFileName);
					FileOutputStream fs = new FileOutputStream(newFileName);
					byte[] buffer = new byte[1444];
					while ((byteread = inStream.read(buffer)) != -1) {
						fs.write(buffer, 0, byteread);
					}
					inStream.close();
					fs.close();
				} else {
					throw new Exception("file not found");
				}
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	/**
	 * 文件的写入
	 *
	 * @param filePath(文件路径)
	 * @param fileName(文件名)
	 * @param args[]
	 * @throws IOException
	 */
	public static void writeFile(String filePath, String fileName, String[] args) throws IOException {
		FileWriter fw = new FileWriter(filePath + fileName);
		PrintWriter out = new PrintWriter(fw);
		for (int i = 0; i < args.length; i++) {
			out.write(args[i]);
			out.println();
			out.flush();
		}
		fw.close();
		out.close();
	}

	/**
	 * 文件的写入
	 *
	 * @param      filePath(文件路径)
	 * @param      fileName(文件名)
	 * @param args
	 * @throws IOException
	 */
	public static void writeFile(String filePath, String fileName, List<String> args) throws IOException {
		File dir = new File(filePath);
		if (!dir.exists()) {
			dir.mkdirs();
		}

		FileWriter fw = new FileWriter(filePath + fileName);
		PrintWriter out = new PrintWriter(fw);
		for (int i = 0; i < args.size(); i++) {
			out.write(args.get(i));
			out.println();
			out.flush();
		}
		fw.close();
		out.close();
	}

	/**
	 * 文件的写入
	 *
	 * @param      filePath(文件路径)
	 * @param      fileName(文件名)
	 * @param args
	 * @throws IOException
	 */
	public static void writeFile(String filePath, String fileName, String args) throws IOException {
		writeFile(filePath + fileName, args);
	}

	/**
	 * 文件的写入
	 *
	 * @param      filePath(文件路径)
	 * @param      fileName(文件名)
	 * @param args
	 * @throws IOException
	 */
	public static void writeFile(String fullFileName, String args) throws IOException {
		FileWriter fw = new FileWriter(fullFileName);
		fw.write(args);
		fw.close();
	}

	/**
	 * 创建与删除文件
	 *
	 * @param filePath
	 * @param fileName
	 * @return 创建成功返回true
	 * @throws IOException
	 */
	public static boolean createAndDeleteFile(String filePath, String fileName) throws IOException {
		boolean result = false;
		File file = new File(filePath, fileName);
		if (file.exists()) {
			file.delete();
			result = true;
			System.out.println("文件已经删除！");
		} else {
			file.createNewFile();
			result = true;
			System.out.println("文件已经创建！");
		}
		return result;
	}

	/**
	 * 创建和删除目录
	 *
	 * @param folderName
	 * @param filePath
	 * @return 删除成功返回true
	 */
	public static boolean createAndDeleteFolder(String folderName, String filePath) {
		boolean result = false;
		try {
			File file = new File(filePath + folderName);
			if (file.exists()) {
				file.delete();
				System.out.println("目录已经存在，已删除!");
				result = true;
			} else {
				file.mkdir();
				System.out.println("目录不存在，已经建立!");
				result = true;
			}
		} catch (Exception ex) {
			result = false;
			System.out.println("CreateAndDeleteFolder is error:" + ex);
		}
		return result;
	}

	/**
	 * 输出目录中的所有文件及目录名字
	 *
	 * @param filePath
	 */
	public static void readFolderByFile(String filePath) {
		File file = new File(filePath);
		File[] tempFile = file.listFiles();
		for (int i = 0; i < tempFile.length; i++) {
			if (tempFile[i].isFile()) {
				System.out.println("File : " + tempFile[i].getName());
			}
			if (tempFile[i].isDirectory()) {
				System.out.println("Directory : " + tempFile[i].getName());
			}
		}
	}

	/**
	 * 检查文件是否为空
	 *
	 * @param filePath
	 * @param fileName
	 * @return 为空返回true
	 * @throws IOException
	 */
	public static boolean fileIsNull(String filePath, String fileName) throws IOException {
		boolean result = false;
		FileReader fr = new FileReader(filePath + fileName);
		if (fr.read() == -1) {
			result = true;
			System.out.println(fileName + " 文件中没有数据!");
		} else {
			System.out.println(fileName + " 文件中有数据!");
		}
		fr.close();
		return result;
	}

	/**
	 * 读取文件中的所有内容
	 *
	 * @param filePath
	 * @param fileName
	 * @throws IOException
	 */
	public static void readAllFile(String filePath, String fileName) throws IOException {
		FileReader fr = new FileReader(filePath + fileName);
		int count = fr.read();
		while (count != -1) {
			System.out.print((char) count);
			count = fr.read();
			if (count == 13) {
				fr.skip(1);
			}
		}
		fr.close();
	}

	/**
	 * 一行一行的读取文件中的数据
	 *
	 * @param filePath
	 * @param fileName
	 * @throws IOException
	 */
	public static void readLineFile(String filePath, String fileName) throws IOException {
		FileReader fr = new FileReader(filePath + fileName);
		BufferedReader br = new BufferedReader(fr);
		String line = br.readLine();
		while (line != null) {
			System.out.println(line);
			line = br.readLine();
		}
		br.close();
		fr.close();
	}

	public static String getRootPath() throws IOException {
		File f = new File("");
		String path = f.getCanonicalPath();// 标准路径,跟absolutePath只差一个文件名
		System.out.println(">> Path: " + path);

		return path;
	}

	public static boolean delAllFile(String path, String strDate8) {
		File file = new File(path);
		if (!file.exists()) {
			return false;
		}
		if (!file.isDirectory()) {
			return false;
		}
		String[] tempList = file.list();
		File temp = null;
		String fileTime = null;
		for (int i = 0; i < tempList.length; i++) {
			if (path.endsWith(File.separator)) {
				temp = new File(path + tempList[i]);
			} else {
				temp = new File(path + File.separator + tempList[i]);
			}
			long lastDate = temp.lastModified();
			Date date = new Date(lastDate);
			fileTime = DateUtil.formatDate(date, "yyyyMMdd");

			if (strDate8.compareTo(fileTime) >= 0) {
				if (temp.isFile()) {
					temp.delete();
				}
				if (temp.isDirectory()) {
					temp.delete();
					// delFolder(path + File.separatorChar + tempList[i]);//
					// 再删除空文件夹
				}
			}

		}
		return false;
	}

	public static boolean deleteFile(String fullPathFileName) {
		File file = new File(fullPathFileName);
		if (!file.exists()) {
			System.out.println("FILE: [" + fullPathFileName + "] 不存在。无法删除。");
			return false;
		}
		if (!file.isFile()) {
			System.out.println("[" + fullPathFileName + "] 不是文件。无法删除。");
			return false;
		}

		System.out.println("DELETE FILE: [" + fullPathFileName + "]");
		return file.delete();
	}

	public static boolean mkdirs(String path) {
		File file = new File(path);
		if (!file.exists()) {
			System.out.println("PAHT: [" + path + "] 不存在！创建目录！");
			return file.mkdirs();
		}
		return true;
	}

	public static File[] getSubFiles(String path) {
		File file = new File(path);
		if (!file.exists()) {
			System.out.println("PAHT: [" + path + "] 不存在！创建目录！");
			return null;
		}
		return file.listFiles();
	}

	/**
	 * <p>
	 * 将文件转成base64 字符串
	 * </p>
	 *
	 * @param path 文件路径
	 * @return
	 * @throws Exception
	 */
	public static String encodeBase64File(String path) throws Exception {
		File file = new File(path);
		if (!file.exists()) {
			return null;
		}
		FileInputStream inputFile = new FileInputStream(file);
		byte[] buffer = new byte[(int) file.length()];
		inputFile.read(buffer);
		inputFile.close();
		return new BASE64Encoder().encode(buffer);
	}

	/**
	 * 文件的移动
	 *
	 * @param oldFileName(原文件路径)
	 * @param newPath(目标文件路径(不含文件名))
	 * @param newFileName(目标文件名)
	 * @throws IOException
	 */
	public static void moveFile(String oldFileName, String newPath, String newFileName) {

		File newFilePath = new File(newPath);
		newFilePath.mkdirs();

		File oldfile = new File(oldFileName);
		newFileName = newPath + newFileName;

		if (!oldFileName.equals(newFileName)) {
			if (oldfile.exists()) {
				oldfile.renameTo(new File(newFileName));
			}
		}
	}

	public static int deleteFile(List<String> fullPathList, boolean ignorError) {

		int count = 0;

		for (int i = 0; i < fullPathList.size(); i++) {

			try {
				if (deleteFile(fullPathList.get(i))) {
					count++;
				}
			} catch (Exception ex) {

				if (!ignorError) {
					return -1;
				}
			}
		}

		return count;
	}

	/**
	 * 文件是否存在
	 * 
	 * @param path：文件完成路径（含路径、文件名、文件类型）
	 * @return 如果路径是文件并且存在，返回true; 其他，返回false
	 */
	public static boolean fileExists(String path) {

		File file = new File(path);

		return (file.isFile() && file.exists());
	}
}
