package com.product.food.exception;

import com.product.food.model.Code;
import com.product.food.model.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class MyExceptionHandler {
    private Logger logger = LoggerFactory.getLogger(MyExceptionHandler.class);
    private static final String NO_RECORDS = "没有这条记录！";
    private static final String NOT_IMAGE = "非法图片！";
    private static final String EMPTY_PARAM = "参数有空值！";
    private static final String GET_USERNAME_ERROR = "获取用户名失败！";
    @ExceptionHandler(MyException.class)
    public String getMessage(Exception e) {
        logger.info(e.getMessage());
        switch (e.getMessage()) {
            case "NOT_IMAGE":
                return getResponse(Code.WRONG_PARAMS, NOT_IMAGE);
            case "EMPTY_PARAM":
                return getResponse(Code.WRONG_PARAMS,EMPTY_PARAM);
            case "GET_USERNAME_ERROR":
                return getResponse(Code.SERVER_ERROR,GET_USERNAME_ERROR);
            default:
                return null;
        }
    }

    public String getResponse(Integer code, String message) {
        JSON json = new JSON(code, message);
        return json.getJSON();
    }

}
