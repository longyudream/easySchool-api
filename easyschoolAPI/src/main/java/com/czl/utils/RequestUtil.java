package com.czl.utils;

import com.alibaba.fastjson.JSONObject;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Request工具类
 *
 * @author qmy
 * @since 2019/8/5 14:58
 */
@Slf4j
public class RequestUtil {

    /** 请求参数中的head名 */
    private static final String REQUEST_HEAD = "head";

    /** 请求参数中的body名 */
    private static final String REQUEST_BODY = "body";

    private static Map<String, String[]> getParameterMap(HttpServletRequest req) {
        return req.getParameterMap();
    }

    /**
     * 拿取请求中的参数
     *
     * @param req
     * @return
     */
    public static JSONObject getParam(HttpServletRequest req) {
        JSONObject resultJsonObject = new JSONObject();
        try {
            Map<String, String[]> parameterMap = getParameterMap(req);
            if (parameterMap.size() == 0) {
                log.debug("传入参数为NULL, 执行结束!");
                resultJsonObject.put("resCode", "1111");
                resultJsonObject.put("resMessage", "参数为NULL");
                return resultJsonObject;
            }
            String[] heads = parameterMap.get(REQUEST_HEAD);
            if (heads.length == 0) {
                log.debug("参数heads为NULL, 执行结束!");
                resultJsonObject.put("resCode", "1111");
                resultJsonObject.put("resMessage", "参数heads为NULL");
                return resultJsonObject;
            }
            // 取得head
            String head = heads[0];
            String[] bodys = parameterMap.get(REQUEST_BODY);
            if (bodys.length == 0){
                log.debug("参数bodys为NULL, 执行结束!");
                resultJsonObject.put("resCode", "1111");
                resultJsonObject.put("resMessage", "参数bodys为NULL");
                return resultJsonObject;
            }
            // 取得body
            String body = bodys[0];
            // 组装为正常请求格式
            JSONObject headJsonObject = JSONObject.parseObject(head);
            JSONObject bodyJsonObject = JSONObject.parseObject(body);
            JSONObject paramJsonObject = new JSONObject();
            paramJsonObject.put(REQUEST_HEAD, headJsonObject);
            paramJsonObject.put(REQUEST_BODY, bodyJsonObject);

            resultJsonObject.put("resCode", "0000");
            resultJsonObject.put("resMessage", "参数获取成功!");
            resultJsonObject.put("resInfo", paramJsonObject);
            return resultJsonObject;
        } catch (Exception e) {
            log.debug("参数获取失败, 原因:" + e.getMessage());
            e.printStackTrace();
            resultJsonObject.put("resCode", "1111");
            resultJsonObject.put("resMessage", "参数获取失败");
            return resultJsonObject;
        }
    }


    /**
     * 拿取请求中的body
     *
     * @param req
     * @return
     */
    public static JSONObject getBody(HttpServletRequest req) {
        try {

            Map<String, String[]> parameterMap = getParameterMap(req);
            String body = parameterMap.get("body")[0];
            JSONObject bodyJsonObject = JSONObject.parseObject(body);
            return bodyJsonObject;
        } catch (
                Exception e) {
            log.debug("body获取失败, 原因:" + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 根据自定义名称拿取请求中的数据
     *
     * @param req
     * @param jsonObjectName
     * @return
     */
    public static JSONObject getJSONObject(HttpServletRequest req, String jsonObjectName) {
        try {

            Map<String, String[]> parameterMap = getParameterMap(req);
            String stringName = parameterMap.get(jsonObjectName)[0];
            JSONObject jsonObject = JSONObject.parseObject(stringName);
            return jsonObject;
        } catch (Exception e) {
            log.debug("数据获取失败, 原因:" + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}
