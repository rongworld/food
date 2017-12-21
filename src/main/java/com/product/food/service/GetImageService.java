package com.product.food.service;

import com.product.food.exception.MyException;
import com.product.food.utils.ChangePermission;
import com.product.food.utils.CheckUrlUtil;
import org.apache.catalina.core.ApplicationPart;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
public class GetImageService {
    @Value("${comment_img_path}")
    private String save_path;
    private String uuid;
    private Logger logger = LoggerFactory.getLogger(GetImageService.class);
    public File getImg(HttpServletRequest httpServletRequest,String paramName) throws IOException, ServletException, MyException {
        uuid = UUID.randomUUID().toString().replace("-","");
        Part part = httpServletRequest.getPart(paramName);
        if (part == null){
            throw new MyException("NO_IMAGE");
        }


        if (part.getContentType().contains("image")) {
            ApplicationPart ap = (ApplicationPart) part;
            String fileName1 = ap.getSubmittedFileName();
            int path_idx = fileName1.lastIndexOf("\\") + 1;
            String fileName2 = fileName1.substring(path_idx);
            String suffix = fileName2.substring(fileName2.lastIndexOf(".")+1);
            save_path = CheckUrlUtil.check(save_path);
            String path = save_path + uuid+"."+suffix;
            //ChangePermission.ChangeFile(path);

            part.write(path);
            logger.info("save img ok");
            File file = new File(path);
            Runtime.getRuntime().exec("chmod 777 " + path);
            return file;
        }else{
            throw new MyException("NOT_IMAGE");
        }
    }

    public String getContentType(HttpServletRequest httpServletRequest,String paramName) throws IOException, ServletException {
        Part part = httpServletRequest.getPart(paramName);
        return part.getContentType();
    }
}
