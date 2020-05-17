package com.czl.exception;

import com.czl.entity.ResultEntity;

/**
 * @Author czl
 * @Date 2019/11/11 15:05
 * Version v1.0
 * @description ServiceException
 */
public class ServiceException extends RuntimeException{

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private ResultEntity causeEntity;

    public ServiceException() {
        super();
        // TODO Auto-generated constructor stub
    }

    public ServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        // TODO Auto-generated constructor stub
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
        // TODO Auto-generated constructor stub
    }

    public ServiceException(String message) {
        super(message);
        // TODO Auto-generated constructor stub
    }

    public ServiceException(Throwable cause) {
        super(cause);
        // TODO Auto-generated constructor stub
    }

    public ServiceException(ResultEntity cause) {
        this.causeEntity = cause;
        //TODO
    }

    public ResultEntity getCauseEntity() {
        return causeEntity;
    }

    public void set_causeEntity(ResultEntity causeEntity) {
        this.causeEntity = causeEntity;
    }
}
