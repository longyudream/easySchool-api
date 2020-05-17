package com.czl.utils;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;

/**
 * 处理前端传来的Json字符串
 * @author qmy
 * @since 2019/4/10 10:58
 */
public class JsonConversionUtil {

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

    public static String getHeadNode(String param, String node){
        JSONObject head = getHead(param);
        if (head.containsKey(node)){
            return head.getString(node);
        }else {
            return null;
        }
    }

    public static Map getHeadNode(String param, String... node){
        JSONObject head = getHead(param);
        Map<Object, Object> map = new HashMap<>();
        for (int i = 0; i < node.length; i++) {
            map.put(node[i],head.getString(node[i]));
        }
        return map;
    }

    public static JSONObject getBody(String param){
        if (StringUtil.isEmpty(param)){
            return null;
        }
        return ((JSONObject) JSONObject.parse(param)).getJSONObject("body");
    }

    public static JSONObject getParam(String param){
        return (JSONObject) JSONObject.parse(param);
    }

    public static JSONObject getBodyData(String param){
        return getBody(param).getJSONObject("DATA");
    }

    public static JSONObject getBodyCondition(String param){
        return getBody(param).getJSONObject("condition");
    }

    public static Map getBodyNode(String param, String... columnName){
        JSONObject body = getBody(param);
        Map<Object, Object> map = new HashMap<>();
        for (int i = 0; i < columnName.length; i++) {
            map.put(columnName[i],body.getString(columnName[i]));
        }
        return map;
    }

    public static Map getBodyNode(Map jsonMap, String... columnName) {
        Map<Object, Object> map = new HashMap<>();
        for (int i = 0; i < columnName.length; i++) {
            map.put(columnName[i], jsonMap.get(columnName[i]));
        }
        return map;
    }

    public static Map getBodyDataNode(String param, String... columnName){
        JSONObject data = getBodyData(param);
        Map<Object, Object> map = new HashMap<>();
        for (int i = 0; i < columnName.length; i++) {
            map.put(columnName[i],data.getString(columnName[i]));
        }
        return map;
    }

    public static Map getBodyConditionNode(String param, String... columnName){
        JSONObject condition = getBodyCondition(param);
        Map<Object, Object> map = new HashMap<>();
        for (int i = 0; i < columnName.length; i++) {
            map.put(columnName[i],condition.getString(columnName[i]));
        }
        return map;
    }

    public static Map getAllBodyNode(String param){
        return JSONObject.parseObject(getBody(param).toJSONString(), HashMap.class);
    }

    public static Map getAllBodyDataNode(String param){
        return JSONObject.toJavaObject(getBodyData(param), HashMap.class);
    }

    public static Map getAllBodyConditionNode(String param){
        return JSONObject.toJavaObject(getBodyCondition(param), HashMap.class);
    }
}
