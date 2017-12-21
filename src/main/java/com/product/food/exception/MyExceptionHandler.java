package com.product.food.exception;

import com.product.food.model.Code;
import com.product.food.model.JSON;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class MyExceptionHandler {
    private static final String NO_RECORDS = "没有这条记录！";
    private static final String NO_IMAGE = "没有图片！";
    @ExceptionHandler(MyException.class)
    public String getMessage(Exception e){
        switch (e.getMessage()){
            case  "NO_IMAGE":
                return getResponse(Code.WRONG_PARAMS,NO_IMAGE);
        }
        return null;
    }

    public String getResponse(Integer code,String message){
        JSON json = new JSON(code,message);
        return json.getJSON();
    }

}
