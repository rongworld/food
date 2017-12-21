package com.product.food.service;


import com.product.food.dao.Comment;
import com.product.food.dao.CommentRepository;
import com.product.food.dao.Food;
import com.product.food.dao.FoodRepository;
import com.product.food.exception.MyException;
import com.product.food.model.NewCommentBean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class PublishService {
    private NewCommentBean newCommentBean;

    private Logger logger = LoggerFactory.getLogger(ImageSaveService.class);
    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private FoodRepository foodRepository;

    @Autowired
    private ImageSaveService imageSaveService;

    public Integer publishAndGetFid(NewCommentBean newComment) throws MyException {
        this.newCommentBean = newComment;
        Comment comment = new Comment();
        Food food = foodRepository.
                findBySiteAndShopAndFoodName(newComment.getSite(),
                        newComment.getShop(),
                        newComment.getName()
        );
        if (food != null){
            comment.setFid(food.getId());
            comment.setUsername(newComment.getUsername());
            comment.setScore(newComment.getScore());
            comment.setPublishTime(new Date(newComment.getPublishTime()));
            comment.setContent(newComment.getContent());
            commentRepository.save(comment);
           imageSaveService.saveCommentImg(comment,newCommentBean.getFile());
           return food.getId();
        }else{
            return createFoodAndGetFid(newComment.getSite(),newComment.getShop(),newComment.getName());
        }
    }
    public Integer createFoodAndGetFid(String site, String shop, String name) throws MyException {
        Food food = new Food();
        food.setSite(site);
        food.setShop(shop);
        food.setFoodName(name);
        food = foodRepository.saveAndFlush(food);
        imageSaveService.saveFoodImg(food,newCommentBean.getFile());
        return publishAndGetFid(newCommentBean);
    }
}
