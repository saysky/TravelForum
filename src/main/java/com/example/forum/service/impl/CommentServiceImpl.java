package com.example.forum.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.forum.entity.Comment;
import com.example.forum.enums.CommentTypeEnum;
import com.example.forum.mapper.CommentMapper;
import com.example.forum.service.CommentService;
import com.example.forum.service.DeleteService;
import com.example.forum.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <pre>
 *     回帖业务逻辑实现类
 * </pre>
 *
 * @author saysky
 * @date 2021/3/20
 */
@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private DeleteService deleteService;

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private PostService postService;


    @Override
    public BaseMapper<Comment> getRepository() {
        return commentMapper;
    }

    @Override
    public QueryWrapper<Comment> getQueryWrapper(Comment comment) {
        //对指定字段查询
        QueryWrapper<Comment> queryWrapper = new QueryWrapper<>();
        if (comment != null) {
            if (comment.getUserId() != null && comment.getUserId() != -1) {
                queryWrapper.eq("user_id", comment.getUserId());
            }
            if (StrUtil.isNotBlank(comment.getType())) {
                queryWrapper.eq("type", comment.getType());
            }
            if (comment.getAcceptUserId() != null && comment.getAcceptUserId() != -1) {
                queryWrapper.eq("accept_user_id", comment.getAcceptUserId());
            }
            if (StrUtil.isNotBlank(comment.getCommentContent())) {
                queryWrapper.like("comment_content", comment.getCommentContent());
            }
            if (comment.getCommentParent() != null) {
                queryWrapper.eq("comment_parent", comment.getCommentParent());
            }
            if (comment.getBusinessId() != null && comment.getBusinessId() != -1) {
                queryWrapper.eq("business_id", comment.getBusinessId());
            }
        }
        return queryWrapper;
    }


    @Override
    public List<Comment> findByBusinessIdAndType(Long commentId, CommentTypeEnum commentTypeEnum) {
        return commentMapper.findByBusinessIdAndType(commentId, commentTypeEnum.getCode());
    }

    @Override
    public List<Comment> findByCommentParent(Long commentParent) {
        return commentMapper.findByCommentParent(commentParent);
    }

    @Override
    public Comment insert(Comment comment) {
        commentMapper.insert(comment);
        //修改文章回帖数
        postService.resetCommentSize(comment.getBusinessId());
        return comment;
    }

    @Override
    public Comment update(Comment comment) {
        commentMapper.updateById(comment);
        //修改文章回帖数
        postService.resetCommentSize(comment.getBusinessId());
        return comment;
    }

    @Override
    public void delete(Long commentId) {
       deleteService.deleteComment(commentId);
    }

    @Override
    public Comment insertOrUpdate(Comment comment) {
        if(comment.getId() == null) {
            insert(comment);
        } else {
            update(comment);
        }
        return comment;
    }




}
