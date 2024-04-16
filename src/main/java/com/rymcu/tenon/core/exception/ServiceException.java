package com.rymcu.tenon.core.exception;


import com.rymcu.tenon.core.result.ResultCode;
import lombok.Getter;

/**
 * 服务（业务）异常如“ 账号或密码错误 ”，该异常只做INFO级别的日志记录 @see WebMvcConfigurer
 *
 * @author ronger
 */
@Getter
public class ServiceException extends RuntimeException {
    private int code;
    private String extraMessage;

    public ServiceException(ResultCode resultCode) {
        super(resultCode.getMessage());
        this.code = resultCode.getCode();
    }


    public ServiceException(String message, Throwable cause) {

    }

    public ServiceException(int code, String message, String extraMessage, Throwable cause) {
        super(message, cause);
        this.code = code;
        this.extraMessage = extraMessage;
    }


    public ServiceException(ResultCode resultCode, String extraMessage) {
        this(resultCode.getCode(), resultCode.getMessage(), extraMessage, null);
    }

    public ServiceException(String extraMessage) {
        this(ResultCode.INVALID_PARAM, extraMessage);
    }


}
