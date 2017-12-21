package com.product.food.exception;

import com.product.food.model.Code;
import com.product.food.model.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class InternetExceptionHandler {
    private Logger logger = LoggerFactory.getLogger(InternetExceptionHandler.class);
    @ExceptionHandler(value = NullPointerException.class)
    public String getExp(Exception e){
        logger.info(e.getMessage());
        return new JSON(Code.SERVER_ERROR,"Server Error").getJSON();
    }

}
