package com.product.food.service;

import com.product.food.dao.Comment;
import com.product.food.dao.CommentRepository;
import com.product.food.dao.Food;
import com.product.food.dao.FoodRepository;
import com.product.food.utils.CheckUrlUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

@Service
public class ImageSaveService  {

    private Logger logger = LoggerFactory.getLogger(ImageSaveService.class);

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private FoodRepository foodRepository;

    @Value("${food_img_path}")
    private String foodImgPath;


    @Value("${comment_img_url}")
    private String CommentImgUrl;

    @Value("${food_img_url}")
    private String foodImgUrl;


    public boolean saveFoodImg(Food food, File f) {
        foodImgPath = CheckUrlUtil.check(foodImgPath);
        File file = new File(foodImgPath + f.getName());
        try {
            int byteRead;
            if (f.exists()) {
                InputStream inStream = new FileInputStream(f);
                FileOutputStream fs = new FileOutputStream(file);
                byte[] buffer = new byte[1024];
                while ((byteRead = inStream.read(buffer)) != -1) {
                    fs.write(buffer, 0, byteRead);
                }
                inStream.close();
            }
            //file.setReadable(true,false);
            Runtime.getRuntime().exec("chmod 777 " + file.getAbsolutePath());
        } catch (Exception e) {
            e.printStackTrace();
        }

        foodImgUrl = CheckUrlUtil.check(foodImgUrl);
        food.setImgUrl(foodImgUrl +f.getName());
        foodRepository.saveAndFlush(food);
        return true;
    }



    public boolean saveCommentImg(Comment comment, File imgFile) {
        comment.setImgUrls(CommentImgUrl + imgFile.getName());
        commentRepository.saveAndFlush(comment);
        return true;
    }

}