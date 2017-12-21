package com.product.food.security;


import com.product.food.exception.MyException;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

@Component
public class CheckToken {
    @Value("${checkURL}")
    private String checkURL;
    private Logger logger = LoggerFactory.getLogger(CheckToken.class);
    private String response;

    public String getResponse(String token) {
        try {
            //向checkURL请求，请求方式为GET
            URL url = new URL(checkURL + "?token=" + token);
            //http://{checkURL}?token=xxx
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setConnectTimeout(1000);
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setReadTimeout(1000);
            byte[] rp = new byte[1024];
            int n;
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            InputStream inputStream = httpURLConnection.getInputStream();//获得服务器的输输入流

            while ((n = inputStream.read(rp)) != -1) {
                outStream.write(rp, 0, n);
            }
            inputStream.close();
            response = new String(outStream.toByteArray());
            logger.info(response);


        } catch (Exception
                e) {

        }
        return response;
    }

    public boolean check(String token) {
        String r = getResponse(token);
        if (r == null) {
            return false;
        }
        try {
            JSONObject jsonObject = new JSONObject(r);
            jsonObject = new JSONObject(response);
            if (jsonObject.getString("code").equals("0")) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    public String getUsername(String token) throws MyException{
        String r = getResponse(token);
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(r);
            return jsonObject.getString("phoneNumber");
        } catch (JSONException e) {
            throw new MyException("GET_USERNAME_ERROR");
        }
    }
}
