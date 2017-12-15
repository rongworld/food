package com.product.food.security;

import com.product.food.model.JSON;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
public class UserVerify {
    @Autowired
    private  HttpServletRequest httpServletRequest;

    @Value("${loginVerify}")
    private boolean isVerify;

    @Value("${key}")
    private String key;

    @Pointcut("@annotation(com.product.food.annotation.LoginOnly)")
    public void intercept() {
    }

    @Around("intercept()")
    public Object verify(ProceedingJoinPoint pjp) throws Throwable{
        if(!isVerify){
            return pjp.proceed();
        }
        String token = getTokenByHeader(httpServletRequest);
        if(Token.parseJWT(token,key) != null){
           return pjp.proceed();
        }else{
         return new JSON("-1","未登录").getJSON();
        }
    }


    private String getTokenByHeader(HttpServletRequest httpServletRequest) {//从请求头获得token
        return httpServletRequest.getHeader("Authorization");
    }
}
