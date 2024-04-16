package com.rymcu.tenon.core.result;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author ronger
 */
public class GlobalResultGenerator {
    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalResultGenerator.class);

    /**
     * normal
     *
     * @param code
     * @param data
     * @param message
     * @param <T>
     * @return
     */
    public static <T> GlobalResult<T> genResult(Integer code, T data, String message) {
        GlobalResult<T> result = new GlobalResult<>();
        result.setCode(code);
        result.setData(data);
        result.setMessage(message);
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("generate rest result:{}", result);
        }
        return result;
    }

    /**
     * success
     *
     * @param data
     * @param <T>
     * @return
     */
    public static <T> GlobalResult<T> genSuccessResult(T data) {

        return genResult(200, data, null);
    }

    /**
     * error message
     *
     * @param message error message
     * @param <T>
     * @return
     */
    public static <T> GlobalResult<T> genErrorResult(String message) {

        return genResult(400, null, message);
    }

    /**
     * error
     *
     * @param error error enum
     * @param <T>
     * @return
     */
    public static <T> GlobalResult<T> genErrorResult(ResultCode error) {

        return genErrorResult(error.getMessage());
    }

    /**
     * success no message
     *
     * @return
     */
    public static GlobalResult genSuccessResult() {
        return genSuccessResult(null);
    }

    /**
     * success
     *
     * @param <T>
     * @return
     */
    public static <T> GlobalResult<T> genSuccessResult(String message) {

        return genResult(200, null, message);
    }

}
