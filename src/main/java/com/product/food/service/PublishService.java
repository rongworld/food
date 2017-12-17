package com.product.food.service;


import com.product.food.dao.Comment;
import com.product.food.dao.CommentRepository;
import com.product.food.dao.Food;
import com.product.food.dao.FoodRepository;
import com.product.food.model.NewCommentBean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PublishService {

    private Logger logger = LoggerFactory.getLogger(ImageSaveService.class);
    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private FoodRepository foodRepository;

    @Autowired
    private ImageSaveService imageSaveService;

    public Integer publishAndGetFid(NewCommentBean newComment){
        Comment comment = new Comment();
        Food food = foodRepository.
                findBySiteAndShopAndFoodName(newComment.getSite(),
                        newComment.getShop(),
                        newComment.getName()
        );

       //Food food = foodRepository.findFoodByShop("南昌炒粉");

        if (food != null){
            comment.setFid(food.getId());
            comment.setUsername(newComment.getUsername());
            comment.setScore(newComment.getScore());
            comment.setPublishTime(newComment.getPublishTime());
            comment.setContent(newComment.getContent());
            commentRepository.save(comment);
           imageSaveService.setCommentAndNewComment(comment,newComment);
            new Thread(imageSaveService).start();
            return food.getId();
        }else{
            return createFoodAndGetFid(newComment.getSite(),newComment.getShop(),newComment.getName());
        }
    }




    public Integer createFoodAndGetFid(String site, String shop, String name){
        return null;
    }





}
