package com.product.food.service;

import com.product.food.dao.Comment;
import com.product.food.dao.CommentRepository;
import com.product.food.model.NewCommentBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Service
public class ImageSaveService implements Runnable{
    private Comment comment;
    private String UUID;
    private String imageSuffix;
    private Logger logger = LoggerFactory.getLogger(ImageSaveService.class);
    private NewCommentBean newComment;
    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UpImgToServer imgToServer;




    @Override
    public void run() {
        try {
            UUID = upImg(newComment.getFile());
            logger.info("接收到了UUID:"+UUID);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(UUID == null){
            return;
        }
        comment.setImgUrls(UUID+"."+imageSuffix);
        commentRepository.saveAndFlush(comment);
    }

    public void setCommentAndNewComment(Comment comment,NewCommentBean newComment){
        this.comment = comment;
        this.newComment = newComment;
    }




    private String upImg(File file) throws IOException {
        String fileName = file.getName();
        imageSuffix = fileName.substring(fileName.lastIndexOf(".")+1);
      return imgToServer.up(file,imageSuffix,newComment.getImageContentType());
    }



















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
