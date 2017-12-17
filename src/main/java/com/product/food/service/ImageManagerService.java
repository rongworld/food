package com.product.food.service;

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
public class ImageManagerService {
    @Value("${img_save_path}")
    private String save_path;
    private String uuid;
    private Logger logger = LoggerFactory.getLogger(ImageManagerService.class);
    public File getImg(HttpServletRequest httpServletRequest,String paramName) throws IOException, ServletException {
        uuid = UUID.randomUUID().toString().replace("-","");
        Part part = httpServletRequest.getPart(paramName);
        if (part.getContentType().contains("image")) {
            ApplicationPart ap = (ApplicationPart) part;
            String fileName1 = ap.getSubmittedFileName();
            int path_idx = fileName1.lastIndexOf("\\") + 1;
            String fileName2 = fileName1.substring(path_idx);
            String suffix = fileName2.substring(fileName2.lastIndexOf(".")+1);
            String path = save_path + uuid+"."+suffix;
            part.write(path);
            logger.info("ok");
            return new File(path);
        }
        return null;
    }


    public String getContentType(HttpServletRequest httpServletRequest,String paramName) throws IOException, ServletException {
        Part part = httpServletRequest.getPart(paramName);
        return part.getContentType();
    }
}
