package com.czl.utils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.XPath;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.xml.sax.InputSource;

@SuppressWarnings("rawtypes")
public class XmlUtil {

	/**
	 * 按Name取得XML下所有的集合
	 * 
	 * @param document
	 * @param searchPath
	 * @return
	 */
	public static List xmlNodeList(Document document, String searchPath) {
		// 获得命名空间
		Map<String, String> map = new HashMap<String, String>();
		String nsUri = document.getRootElement().getNamespaceURI();
		map.put("xmlns", nsUri);
		String tempPath = "";
		if (nsUri != null && !"".equals(nsUri.trim())) {
			String[] paths = searchPath.split("/");
			for (int i = 1; i < paths.length; i++) {
				tempPath += "/xmlns:" + paths[i];
			}
		} else {
			tempPath = searchPath;
		}

		XPath xPath = document.createXPath(tempPath);
		xPath.setNamespaceURIs(map);
		List nodeList = xPath.selectNodes(document);

		return nodeList;
	}

	public static Element xmlSingleNodeByProperty(Document document, String searchPath, String attributeName, String attributeValue) {
		// 获得命名空间
		Map<String, String> map = new HashMap<String, String>();
		String nsUri = document.getRootElement().getNamespaceURI();
		map.put("xmlns", nsUri);
		String tempPath = "";
		if (nsUri != null && !"".equals(nsUri.trim())) {
			String[] paths = searchPath.split("/");
			for (int i = 1; i < paths.length; i++) {
				tempPath += "/xmlns:" + paths[i];
			}
		} else {
			tempPath = searchPath;
		}

		XPath xPath = document.createXPath(tempPath);
		xPath.setNamespaceURIs(map);
		List nodeList = xPath.selectNodes(document);
		for (int i = 0; i < nodeList.size(); i++) {
			Element node = (Element) nodeList.get(i);
			String attrValue = node.attributeValue(attributeName);
			if (attrValue.equals(attributeValue)) {
				return node;
			}
		}
		return null;
	}

	/**
	 * 从Xml中按指定路径找到单一的节点。
	 * 
	 * @param document
	 * @param searchPath
	 * @return
	 */
	public static Element xmlSingleNode(Document document, String searchPath) {
		// 获得命名空间
		Map<String, String> map = new HashMap<String, String>();
		String nsUri = document.getRootElement().getNamespaceURI();
		map.put("xmlns", nsUri);
		String tempPath = "";
		if (nsUri != null && !"".equals(nsUri.trim())) {
			String[] paths = searchPath.split("/");
			for (int i = 1; i < paths.length; i++) {
				tempPath += "/xmlns:" + paths[i];
			}
		} else {
			tempPath = searchPath;
		}

		XPath xPath = document.createXPath(tempPath);
		xPath.setNamespaceURIs(map);
		Element node = (Element) xPath.selectSingleNode(document);

		return node;
	}

	/**
	 * 从Xml中按指定路径找到单一的节点的Text内容。
	 * 
	 * @param document
	 * @param searchPath
	 * @return
	 */
	public static String xmlSingleNodeText(Document document, String searchPath) {
		// 获得命名空间
		Map<String, String> map = new HashMap<String, String>();
		String nsUri = document.getRootElement().getNamespaceURI();
		map.put("xmlns", nsUri);
		String tempPath = "";
		if (nsUri != null && !"".equals(nsUri.trim())) {
			String[] paths = searchPath.split("/");
			for (int i = 1; i < paths.length; i++) {
				tempPath += "/xmlns:" + paths[i];
			}
		} else {
			tempPath = searchPath;
		}

		XPath xPath = document.createXPath(tempPath);
		xPath.setNamespaceURIs(map);
		Element node = (Element) xPath.selectSingleNode(document);
		if (node == null) {
			return null;
		} else {
			String text = node.getText();
			if (StringUtil.isEmpty(text)) {
				return null;
			} else {
				return text.trim();
			}
		}
	}

	/**
	 * 按条件取得单条数据
	 * 
	 * @param document
	 * @param searchPath
	 * @return
	 */
	private static Element xmlNode(Document document, String searchPath) {
		// 获得命名空间
		Map<String, String> map = new HashMap<String, String>();
		String nsUri = document.getRootElement().getNamespaceURI();
		map.put("xmlns", nsUri);
		String tempPath = "";
		if (nsUri != null && !"".equals(nsUri.trim())) {
			String[] paths = searchPath.split("/");
			for (int i = 1; i < paths.length; i++) {
				tempPath += "/xmlns:" + paths[i];
			}
		} else {
			tempPath = searchPath;
		}

		XPath xPath = document.createXPath(tempPath);
		xPath.setNamespaceURIs(map);
		Element node = (Element) xPath.selectSingleNode(document);
		return node;
	}

	/**
	 * 取得节点属性
	 * 
	 * @param document
	 * @param searchPath
	 * @param property
	 * @return
	 */
	public static String searchNodeProperty(Document document, String searchPath, String property) {
		String result = "";
		Element node = xmlNode(document, searchPath);
		result = node.attributeValue(property);
		return result;
	}

	/**
	 * 从文件获取XmlDocument
	 * 
	 * @param fileName
	 *            Xml文件名称
	 * @return Document
	 * @throws DocumentException
	 */
	public static Document getXmlDocumentByFileName(String fileName) throws DocumentException {
		File inputXml = new File(fileName);
		SAXReader saxReader = new SAXReader();
		Document document = saxReader.read(inputXml);
		return document;
	}

	/**
	 * 从文件获取XmlDocument
	 * 
	 * @param inputXml
	 *            xml文件对象
	 * @return
	 * @throws DocumentException
	 */
	public static Document getXmlDocumentByFile(File inputXml) throws DocumentException {
		SAXReader saxReader = new SAXReader();
		Document document = saxReader.read(inputXml);
		return document;
	}

	/**
	 * 创建Xml文件
	 * 
	 * @param fileName
	 *            文件名称(含路径)
	 * @param document
	 * @param encoding
	 *            字符集
	 * @throws IOException
	 */
	public static void createXmlFile(String fileName, Document document, String encoding) throws IOException {
		OutputFormat format = OutputFormat.createPrettyPrint();
		format.setEncoding(encoding);

		Writer fileWriter = new FileWriter(fileName);
		XMLWriter xmlWriter = new XMLWriter(fileWriter, format);
		xmlWriter.write(document);
		xmlWriter.close();
	}

	/**
	 * 字符串转XmlDocument
	 * 
	 * @param xmlContenct
	 *            xml内容字符串
	 * @param charset
	 *            字符集
	 * @return
	 */
	public static Document createXmlDocument(String xmlContenct, String charset) {
		try {
			SAXReader saxReader = new SAXReader();
			Document document = saxReader.read(new ByteArrayInputStream(xmlContenct.getBytes(charset)));
			return document;
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 截取字符串末尾N位内容
	 * 
	 * @param str
	 *            字符串
	 * @param lastLength
	 *            需要截取的末尾长度
	 * @return
	 * @throws Exception
	 */
	public static String getLastString(String str, int lastLength) throws Exception {
		if (!StringUtil.isEmpty(str)) {
			if (str.length() > lastLength) {
				return str.substring(str.length() - lastLength);
			} else {
				return str;
			}
		} else {
			return "";
		}
	}

	/**
	 * 字符串数值转换为BigDecimal类型
	 * 
	 * @param strValue
	 *            字符串数值
	 * @return BigDecimal
	 * @throws Exception
	 */
	public static BigDecimal stringToDecimal(String strValue) throws Exception {
		if (StringUtil.isEmpty(strValue)) {
			return BigDecimal.ZERO;
		}
		return new BigDecimal(strValue);
	}

	/**
	 * 日期格式转换
	 * 
	 * @param strDate
	 *            字符串日期
	 * @param inputFormat
	 *            输入的日期格式
	 * @param outputFormat
	 *            输出的日期格式
	 * @return
	 */
	public static String convertDate(String strDate, String inputFormat, String outputFormat) {
		if (StringUtil.isEmpty(strDate)) {
			return "";
		}
		Date date = DateUtil.toDate(strDate, inputFormat);
		String returnDate = DateUtil.formatDate(date, outputFormat);
		return returnDate;
	}

	/**
	 * 格式化XML文档
	 *
	 * @param document
	 *            xml文档
	 * @param charset
	 *            字符串的编码
	 * @param istrans
	 *            是否对属性和元素值进行转移(特殊字符不需要转义时用false)
	 * @return 格式化后XML字符串
	 */
	public static String formatXml(Document document, String charset, boolean istrans) {
		OutputFormat format = OutputFormat.createPrettyPrint();
		format.setEncoding(charset);
		StringWriter sw = new StringWriter();
		XMLWriter xw = new XMLWriter(sw, format);
		xw.setEscapeText(istrans);
		try {
			xw.write(document);
			xw.flush();
			xw.close();
		} catch (IOException e) {
			System.out.println("格式化XML文档发生异常，请检查！");
			e.printStackTrace();
		}
		return sw.toString();
	}

	public static String formatXml(Element element, String charset, boolean istrans) {
		OutputFormat format = OutputFormat.createPrettyPrint();
		format.setEncoding(charset);
		StringWriter sw = new StringWriter();
		XMLWriter xw = new XMLWriter(sw, format);
		xw.setEscapeText(istrans);
		try {
			xw.write(element);
			xw.flush();
			xw.close();
		} catch (IOException e) {
			System.out.println("格式化XML文档发生异常，请检查！");
			e.printStackTrace();
		}
		return sw.toString();
	}

	/**
	 * 生成不带头的消xml字符串
	 * 
	 * @param map
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String map2XmlString(Map<String, String> map) throws UnsupportedEncodingException {
		String xmlResult = "";
		StringBuffer sb = new StringBuffer();
		sb.append("<xml>");
		for (String key : map.keySet()) {
			String value = map.get(key);
			// value = new String(value.getBytes("UTF-8"));
			sb.append("<" + key + ">" + value + "</" + key + ">");
		}
		sb.append("</xml>");
		xmlResult = sb.toString();
		return xmlResult;
	}

	/**
	 * 传入xml格式的字符串, 返回XmlDocument
	 * 
	 * @param xmlContent
	 *            xml格式的字符串
	 * @return
	 * @throws Exception
	 */
	public static org.w3c.dom.Document getXmlDocument(String xmlContent) throws Exception {

		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		StringReader sr = new StringReader(xmlContent);
		InputSource is = new InputSource(sr);
		return db.parse(is);
	}

	/**
	 * 取得节点内的值
	 * 
	 * @param document
	 * @param nodeName
	 * @return
	 */
	public static String getNodeContent(org.w3c.dom.Document document, String nodeName) {
		org.w3c.dom.NodeList node = document.getElementsByTagName(nodeName);
		if (node != null && node.getLength() > 0) {
			return node.item(0).getTextContent();
		}
		return "";
	}

}