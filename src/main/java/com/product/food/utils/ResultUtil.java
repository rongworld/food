package com.product.food.utils;

import java.util.HashMap;

public class ResultUtil {
    private HashMap<String,String> resultMap;
    public ResultUtil(String code,String message){
        resultMap = new HashMap();
        resultMap.put("code",code);
        resultMap.put("message",message);
    }
    public HashMap getResult(){
        return resultMap;
    }

    public ResultUtil(String code,String message,HashMap<String,String> hashMap){



    }



}
