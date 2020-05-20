package com.czl.utils;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;

/**
 * 对前端传来的jason参数处理
 * @author 陈正龙
 * @date 2020/5/20 17:42
 */
public class JsonConversionUtil {

    /**
     * 将请求头转换城json格式
     * @param param
     * @return
     */
    public static JSONObject getHead(String param){
		// 判断参数是否为空
		if (StringUtil.isEmpty(param)) {
			return null;
		}
		// 验证是否为JSON字符串
		try {
			JSONObject jsonObject = ((JSONObject) JSONObject.parse(param)).getJSONObject("head");
			return jsonObject;
		} catch (Exception e) {
			return null;
		}
    }

    /**
     * 从请求参数中获取某个节点
     * @param param
     * @param node 节点名
     * @return
     */
    public static String getHeadNode(String param, String node){
        JSONObject head = getHead(param);
        if (head.containsKey(node)){
            return head.getString(node);
        }else {
            return null;
        }
    }

    /**
     * 从json格式的字符串中获取多个节点的信息 封装在map中
     * @param param
     * @param node
     * @return
     */
    public static Map getHeadNode(String param, String... node){
        JSONObject head = getHead(param);
        Map<Object, Object> map = new HashMap<>();
        for (int i = 0; i < node.length; i++) {
            map.put(node[i],head.getString(node[i]));
        }
        return map;
    }

    /**
     * 获取请求体Body的信息
     * @param param
     * @return
     */
    public static JSONObject getBody(String param){
        if (StringUtil.isEmpty(param)){
            return null;
        }
        return ((JSONObject) JSONObject.parse(param)).getJSONObject("body");
    }

    /**
     * 将请求信息转化为json对象
     * @param param
     * @return
     */
    public static JSONObject getParam(String param){
        return (JSONObject) JSONObject.parse(param);
    }

    /**
     * 获取Body中携带的多个参数,封装在Map中
     * @param param
     * @param columnName
     * @return
     */
    public static Map getBodyNode(String param, String... columnName){
        JSONObject body = getBody(param);
        Map<Object, Object> map = new HashMap<>();
        for (int i = 0; i < columnName.length; i++) {
            map.put(columnName[i],body.getString(columnName[i]));
        }
        return map;
    }
}
