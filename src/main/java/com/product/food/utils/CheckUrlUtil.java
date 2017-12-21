package com.product.food.utils;

public class CheckUrlUtil {

    public static String check(String url){
        if(!url.endsWith("/")){
            return url+"/";
        }
        return url;
    }
}
