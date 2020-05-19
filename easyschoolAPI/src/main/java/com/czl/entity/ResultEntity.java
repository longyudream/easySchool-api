package com.czl.entity;

import com.alibaba.fastjson.JSONObject;
import com.czl.config.MessageListenerConfig;

public class ResultEntity {

    public static final String RESULT_CODE_OK = "0";

    public static final String RESULTENTITY_CODE_OK = "0000";

    public static final String RESULT_CODE_ERROR = "-1";

    public static final String DEFAULT_ERROR_MESSAGEID = "EB1";

    public static final String DEFAULT_SUCCESS_MESSAGEID = "EB1";

    private String status;

    private String messageId;

    private String message;

    private Object entityList;

    public ResultEntity() {
        this.status = ResultEntity.RESULT_CODE_ERROR;
    }


    /**
     * @param
     * @Description:
     * @author caoyl
     * @date 2020-05-19 11:34:59
     */
    public ResultEntity(String status) {
        this.status = status;
        this.messageId = status == ResultEntity.RESULT_CODE_OK ? ResultEntity.DEFAULT_SUCCESS_MESSAGEID
                : ResultEntity.DEFAULT_ERROR_MESSAGEID;

    }

    /**
     * 此方法仅限内部使用 message与messageid一一对应不允许是直接出入message
     *
     * @Description:
     * @param
     * @param message
     * @author caoyl
     * @date 2020-05-19 11:06:59
     */
    public ResultEntity(String internalStatus, String message) {
        if (ResultEntity.RESULT_CODE_OK.equals(internalStatus)) {
            internalStatus = ResultEntity.RESULTENTITY_CODE_OK;
        }
        this.status = internalStatus == ResultEntity.RESULTENTITY_CODE_OK ? "0" : "-1";
        this.messageId = internalStatus == ResultEntity.RESULTENTITY_CODE_OK ? ResultEntity.DEFAULT_SUCCESS_MESSAGEID
                : ResultEntity.DEFAULT_ERROR_MESSAGEID;
        this.message = message;
    }



    /**
     * @param status
     * @param messageId
     * @param msgParams
     * @Description:
     * @author caoyl
     * @date 2020-05-19 11:09:00
     */
    public ResultEntity(String status, String messageId, String[] msgParams) {
        this.status = status;
        // 到资源文件中查找消息并设定
        this.message = MessageListenerConfig.getProperty(messageId, msgParams);
    }

    public ResultEntity(String status, String messageId, String[] msgParams, Object entityList) {
        this.status = status;
        // 到资源文件中查找消息并设定
        this.message = MessageListenerConfig.getProperty(messageId, msgParams);
        this.entityList = entityList;
    }

    public String toJson() {
        return JSONObject.toJSONString(this, true);
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
        this.message = MessageListenerConfig.getProperty(messageId, null);
    }

    public void setMessageIdAndParamsMessage(String messageId, String[] msgParams) {
        this.messageId = messageId;
        this.message = MessageListenerConfig.getProperty(messageId, msgParams);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getEntityList() {
        return entityList;
    }

    public void setEntityList(Object entityList) {
        this.entityList = entityList;
    }

}
