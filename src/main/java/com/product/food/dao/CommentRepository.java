package com.product.food.dao;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;


public interface CommentRepository extends JpaRepository<Comment,Integer>,JpaSpecificationExecutor<Comment> {
    List<Comment> findCommentByPublishTime(Long time);


}
