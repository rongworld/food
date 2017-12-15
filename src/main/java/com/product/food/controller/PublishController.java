package com.product.food.controller;

import com.product.food.annotation.LoginOnly;
import com.product.food.dao.Comment;
import com.product.food.model.JSON;
import com.product.food.model.NewCommentBean;
import com.product.food.security.Token;
import com.product.food.service.PublishService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

@RestController
public class PublishController {
    private HttpServletRequest httpServletRequest;
    @Value("${key}")
    private String key;
    @Autowired
    private PublishService publishService;
    private Logger logger = LoggerFactory.getLogger(PublishController.class);
    @PostMapping(value = "/api/newComment")
    @LoginOnly
    public String publish(@RequestParam("score")String score,
                          @RequestParam("name") String name,
                          @RequestParam("content")String content,
                          @RequestParam("site")String site,
                          @RequestParam("shop")String shop,
                          @RequestParam("img") MultipartFile multipartFile
                          ,HttpServletRequest httpServletRequest
                          ){
        logger.info(score+"\n");
        logger.info(name+"\n");
        logger.info(site+"\n");
        logger.info(shop+"\n");
        this.httpServletRequest = httpServletRequest;
        NewCommentBean newComment = new NewCommentBean();
        newComment.setContent(content);
        newComment.setPublishTime(System.currentTimeMillis());
        newComment.setScore(Float.valueOf(score));
        newComment.setUsername(getUsername());
        newComment.setName(name);
        newComment.setSite(site);
        newComment.setShop(shop);
        newComment.setMultipartFile(multipartFile);
        Integer fid = publishService.publishAndGetFid(newComment);
        JSON json = new JSON("0","successful");
        json.setKeyAndValue("fid",fid);
        return json.getJSON();
    }


    public String getUsername(){
        String username = Token.parseJWT(httpServletRequest.getHeader("Authorization"),key);
        return username;
    }

}
