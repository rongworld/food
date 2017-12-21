package com.product.food.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class ChangePermission {
    private static final Logger logger = LoggerFactory.getLogger(ChangePermission.class);

    public static Process process;

    public static void ChangeFile(String FilePath){
        try {
            process = Runtime.getRuntime().exec("chmod 777 "+FilePath);
            getPrint(process);
        } catch (IOException e) {
            e.printStackTrace();
            logger.info("执行失败！");
        }

    }
    public static void ChangeDir(String DirPath){
        try {
           process = Runtime.getRuntime().exec("chmod -r 777 "+DirPath);
            getPrint(process);
        } catch (IOException e) {
            e.printStackTrace();
            logger.info("执行失败！");
        }
    }

    public static void getPrint(Process process)throws IOException{
        if(process != null){
            InputStream inputStream = process.getInputStream();
            byte [] rp = new byte[1024];
            int n;
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
             while ((n = inputStream.read(rp)) != -1){
                outStream.write(rp,0,n);
            }
            inputStream.close();
             String string = new String(outStream.toByteArray());
             logger.info(string);
        }
    }


}
