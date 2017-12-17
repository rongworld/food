package com.product.food.service;

import com.product.food.utils.FileToByteUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class UpImgToServer {
    @Value("${img_server}")
    private String serverURL;

    public String up(File file,String suffix,String contentType) throws IOException {
        URL url = new URL(serverURL+"?suffix="+suffix);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setConnectTimeout(10000);
        httpURLConnection.setRequestMethod("POST");
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setDoInput(true);
        httpURLConnection.setReadTimeout(10000);
        httpURLConnection.setRequestProperty("Content-Type",contentType);
        OutputStream outputStream = httpURLConnection.getOutputStream();
        outputStream.write(FileToByteUtil.file2byte(file));
        outputStream.close();
        byte [] rp = new byte[1024];
        InputStream inputStream = httpURLConnection.getInputStream();
        inputStream.read(rp);
        inputStream.close();
        return new String(rp);
    }



}
