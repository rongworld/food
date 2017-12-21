package com.product.food.security;

import com.product.food.model.Code;
import com.product.food.model.JSON;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
public class UserVerify {


    @Autowired
    private CheckToken checkToken;

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
        System.out.print(token);
        if(checkToken.check(token)){
           return pjp.proceed();
        }else{
         return new JSON(Code.PERMISSION_DENIED,"未登录").getJSON();
        }
    }


    private String getTokenByHeader(HttpServletRequest httpServletRequest) {//从请求头获得token
        return httpServletRequest.getHeader("Authorization");
    }
}
