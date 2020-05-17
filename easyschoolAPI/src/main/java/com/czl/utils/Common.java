package com.czl.utils;

import java.math.BigDecimal;
import java.net.URLDecoder;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;

import com.alibaba.fastjson.JSONObject;
import com.czl.entity.RequestParamModel;

public class Common {

	public static RequestParamModel CheckRequestParams(String param) {

		RequestParamModel result = null;
		try {
			result = new RequestParamModel();
			JSONObject jsonParam = null;
			try {
				String urlDeCode = URLDecoder.decode(param, "UTF-8");
				if (!StringUtil.isEmpty(urlDeCode) && urlDeCode.endsWith("=")) {
					urlDeCode = urlDeCode.substring(0, urlDeCode.length() - 1);
				}
				jsonParam = (JSONObject) JSONObject.parse(urlDeCode);
			} catch (Exception e) {
				return null;
			}

			result.setBody(jsonParam);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	public static BigDecimal StringToDecimal2(String string) {

		if (StringUtil.isEmpty(string) == true) {
			return null;
		}

		return new BigDecimal(string);
	}

	public static BigDecimal StringToDecimal(String string) {

		if (string == null) {
			return null;
		}

		return new BigDecimal(string);
	}

	public static String ListToString(ArrayList<String> list, String separater) {

		StringBuffer builder = new StringBuffer();

		if (list != null && list.size() > 0) {

			for (int i = 0; i < list.size(); i++) {

				if (i > 0) {
					builder.append(separater);
				}

				builder.append(list.get(i));
			}
		}

		return builder.toString();
	}

	public static ArrayList<String> StringToList(String string, String separater) {

		ArrayList<String> list = new ArrayList<String>();

		if (StringUtil.isEmpty(string) == false) {

			String[] values = string.split(separater);

			for (int i = 0; i < values.length; i++) {
				list.add(values[i]);
			}
		}

		return list;
	}

	public static String unescape(String src) {
		StringBuffer tmp = new StringBuffer();
		tmp.ensureCapacity(src.length());
		int lastPos = 0, pos = 0;
		char ch;
		while (lastPos < src.length()) {
			pos = src.indexOf("%", lastPos);
			if (pos == lastPos) {
				if (src.charAt(pos + 1) == 'u') {
					ch = (char) Integer.parseInt(src.substring(pos + 2, pos + 6), 16);
					tmp.append(ch);
					lastPos = pos + 6;
				} else {
					ch = (char) Integer.parseInt(src.substring(pos + 1, pos + 3), 16);
					tmp.append(ch);
					lastPos = pos + 3;
				}
			} else {
				if (pos == -1) {
					tmp.append(src.substring(lastPos));
					lastPos = src.length();
				} else {
					tmp.append(src.substring(lastPos, pos));
					lastPos = pos;
				}
			}
		}
		return tmp.toString();
	}

	/**
	 * 百分比转化为小数
	 * 
	 * @param percent
	 * @return
	 * @throws ParseException
	 */
	public static BigDecimal percentToFloatValue(String percent) throws ParseException {

		NumberFormat nf = NumberFormat.getPercentInstance();
		Number m = nf.parse(percent);
		return new BigDecimal(m.doubleValue());
	}
}
