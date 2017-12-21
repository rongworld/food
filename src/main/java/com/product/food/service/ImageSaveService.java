package com.product.food.service;

import com.product.food.dao.Comment;
import com.product.food.dao.CommentRepository;
import com.product.food.dao.Food;
import com.product.food.dao.FoodRepository;
import com.product.food.model.NewCommentBean;
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
    private Comment comment;
    private String UUID;
    private String imageSuffix;
    private Logger logger = LoggerFactory.getLogger(ImageSaveService.class);
    private NewCommentBean newComment;
    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private FoodRepository foodRepository;
    /*
        @Autowired
        private UpImgToServer imgToServer;
    */

    @Value("${food_img_path}")
    private String foodImgPath;


    @Value("${comment_img_url}")
    private String CommentImgUrl;

    @Value("${food_img_url}")
    private String foodImgUrl;

/*
    @Override
    public void run() {
        // UUID = upImg(newComment.getFile());
        UUID = newComment.getFile().getName();
        logger.info("UUID:" + UUID);

        if (!CommentImgUrl.endsWith("/")) {
            CommentImgUrl = CommentImgUrl + "/";
        }
        comment.setImgUrls(CommentImgUrl + UUID);
        commentRepository.saveAndFlush(comment);
    }

    public void setCommentAndNewComment(Comment comment, NewCommentBean newComment) {
        this.comment = comment;
        this.newComment = newComment;
    }

*/
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



/*
    private String upImg(File file) throws IOException {
        String fileName = file.getName();
        imageSuffix = fileName.substring(fileName.lastIndexOf(".")+1);
      return imgToServer.up(file,imageSuffix,newComment.getImageContentType());


    }

    */












/*
    private String upImg(MultipartFile m) throws IOException {
        String fileName = m.getOriginalFilename();
        imgContentType = m.getContentType();
        logger.info(fileName);
        logger.info(m.getContentType());
        int dot = fileName.lastIndexOf(".");
        imageSuffix = fileName.substring(dot+1);
        UUID = UUIDUtil.getUUID();
        File file = new File(UUID+"."+imageSuffix);

        if(file.exists() == false){
            file.createNewFile();
            logger.info("文件创建成功，目录："+file.getAbsolutePath());
        }else{
            logger.info("文件已存在，创建失败");
        }
        logger.info(file.getAbsolutePath());
        byte bytes[] = m.getBytes();
        BufferedOutputStream buffStream =
                new BufferedOutputStream(new FileOutputStream(file));
        buffStream.write(bytes);
        logger.info("创建成功");
        buffStream.close();
       String newUUID =  imgToServer.up(file,imageSuffix,imgContentType);
        logger.info(newUUID);
        return UUID;
    }*/


}