package com.product.food.service;

import com.product.food.dao.Comment;
import com.product.food.dao.CommentRepository;
import com.product.food.model.NewCommentBean;
import com.product.food.utils.UUIDUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Service
public class ImgService implements Runnable{
    private Comment comment;
    private String UUID;
    private String imageSuffix;
    private Logger logger = LoggerFactory.getLogger(ImgService.class);
    private NewCommentBean newComment;
    @Value("${SAVE_URL}")
    private  String SAVE_URL;
    @Autowired
    private CommentRepository commentRepository;




    @Override
    public void run() {
        try {
            UUID = upToQiNiu(newComment.getMultipartFile());
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

    private String upToQiNiu(MultipartFile m) throws IOException {
        String fileName = m.getOriginalFilename();
        logger.info(fileName);
        logger.info(m.getContentType());
        int dot = fileName.lastIndexOf(".");
        imageSuffix = fileName.substring(dot+1);
        UUID = UUIDUtil.getUUID();
        File file = new File(SAVE_URL+UUID+"."+imageSuffix);
        if(!file.getParentFile().exists()){
            file.getParentFile().mkdirs();
        }
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
        return UUID;
    }
}
