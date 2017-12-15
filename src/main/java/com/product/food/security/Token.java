package com.product.food.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import javax.xml.bind.DatatypeConverter;

public class Token {
    public static String createToken(Long Exp){
        return null;
    }

    public static String parseJWT(String jwt,String key) {
/*
*会抛出MalformedJwtException(格式不对)、
*SignatureException(无法解密)、
*ExpiredJwtException(超时)等异常，统一用Exception捕获返回false
* */
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(DatatypeConverter.parseBase64Binary(key))
                    .parseClaimsJws(jwt).getBody();
            String id = claims.getId();
           return id;
        } catch (Exception e) {
            return null;
        }
    }
}
