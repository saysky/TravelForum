package com.example.forum.service;


import com.example.forum.common.base.BaseService;
import com.example.forum.entity.Comment;
import com.example.forum.enums.CommentTypeEnum;

import java.util.List;

/**
 * <pre>
 *     回帖业务逻辑接口
 * </pre>
 *
 * @author saysky
 * @date 2021/3/20
 */
public interface CommentService extends BaseService<Comment, Long> {


    /**
     * 根据业务ID和类型获得评论列表
     * @param commentId
     * @return
     */
    List<Comment> findByBusinessIdAndType(Long commentId, CommentTypeEnum commentTypeEnum);


    /**
     * 根据父评论查询
     * @param commentParent
     * @return
     */
    List<Comment> findByCommentParent(Long commentParent);
}
