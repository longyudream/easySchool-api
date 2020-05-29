package com.czl.controller;

import com.alibaba.fastjson.JSONObject;
import com.czl.base.constants.SystemConstants;
import com.czl.entity.ResultEntity;
import com.czl.exception.ServiceException;
import com.czl.utils.JsonSchemaUtil;
import com.czl.utils.StringUtil;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author ACER
 * @Date 2019/11/13 13:57 Version v1.0
 * @description 捕获service自定义异常类
 */
@Slf4j
@ControllerAdvice
public abstract class BaseController {

    @Autowired
    private HttpServletRequest request; // 自动注入request

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Object handleException(Exception e) {
        if (e instanceof ServiceException) {
            return ((ServiceException) e).getCauseEntity();
        } else {
            ResultEntity result = new ResultEntity("1111", "server.status.500", null, null);
            e.printStackTrace();
            return result;
        }
    }

    /**
     * 输入验证
     *
     * @param param
     * @param schemaName
     * @return
     */
    protected ResultEntity inputValid(String param, String schemaName) {


        // 2.输入参数检查
        // 2.1、验证是否符合验证文件
        try {

            if (StringUtil.isNotEmpty(schemaName)) {
                String validJson = JsonSchemaUtil.validJson(schemaName, param);

                // 验证失败
                if (validJson != null) {
                    log.debug("==============校验不通过 原因为：" + validJson);
                    JSONObject jsonObject = JSONObject.parseObject(validJson);
                    String errorMessage = jsonObject.getString("message");
                    log.debug("Json验证未通过：[" + errorMessage + "]！");
                    return new ResultEntity(ResultEntity.RESULT_CODE_ERROR, "ApiMsg3001",
                            new String[] { errorMessage }, null);
                }
            }
        } catch (Exception e) {
            log.debug("=============json参数非法");
            ResultEntity entity = new ResultEntity(ResultEntity.RESULT_CODE_ERROR, "ApiMsg3002", null);
            log.debug(entity.toJson());
            return entity;
        }

        return new ResultEntity(ResultEntity.RESULT_CODE_OK);
    }

    /**
     * 从请求头获取用户编号
     *
     * @return
     */
    protected String getUserId() {
        return request.getHeader(SystemConstants.REQUEST_HEAD_ACCOUNT);
    }

    /**
     * 取得请求头
     *
     * @return
     */
    protected HttpServletRequest getRequest() {
        return request;
    }
}
