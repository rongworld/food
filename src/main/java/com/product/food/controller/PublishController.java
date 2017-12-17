package com.product.food.controller;

import com.product.food.annotation.LoginOnly;
import com.product.food.model.JSON;
import com.product.food.model.NewCommentBean;
import com.product.food.security.Token;
import com.product.food.service.ImageManagerService;
import com.product.food.service.PublishService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RestController
public class PublishController {
    private HttpServletRequest httpServletRequest;
    @Value("${key}")
    private String key;
    @Autowired
    private PublishService publishService;
    @Autowired
    private ImageManagerService imageManagerService;
    private Logger logger = LoggerFactory.getLogger(PublishController.class);
    @PostMapping(value = "/api/newComment")
    @LoginOnly
    public String publish(@RequestParam("score")String score,
                          @RequestParam("name") String name,
                          @RequestParam("content")String content,
                          @RequestParam("site")String site,
                          @RequestParam("shop")String shop
                          ,HttpServletRequest httpServletRequest
                          ) throws IOException, ServletException {
        logger.info(score+"\n"+name+"\n"+content+"\n"+site+"\n"+shop);
        this.httpServletRequest = httpServletRequest;
        NewCommentBean newComment = new NewCommentBean();
        newComment.setContent(content);
        newComment.setPublishTime(System.currentTimeMillis());
        newComment.setScore(Float.valueOf(score));
        newComment.setUsername(getUsername());
        newComment.setName(name);
        newComment.setSite(site);
        newComment.setShop(shop);
        newComment.setFile(imageManagerService.getImg(httpServletRequest,"img"));
        newComment.setImageContentType(imageManagerService.getContentType(httpServletRequest,"img"));
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
