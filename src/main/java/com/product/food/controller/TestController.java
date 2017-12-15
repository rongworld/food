package com.product.food.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;

@RestController
public class TestController {

    private Logger logger = LoggerFactory.getLogger(TestController.class);
    @Value("${SAVE_URL}")
    private String url ;

    @GetMapping(value = "/f")
    public String getMsg() throws IOException {
        File file = new File(url+"text.txt");
        logger.info(file.getAbsolutePath());
        logger.info(file.getName());
        logger.info(file.getCanonicalPath());




        return file.getAbsolutePath();
    }
}
