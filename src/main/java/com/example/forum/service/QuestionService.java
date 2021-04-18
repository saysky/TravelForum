package com.example.forum.service;


import com.example.forum.common.base.BaseService;
import com.example.forum.entity.Question;
import com.example.forum.entity.User;

/**
 * <pre>
 *     帖子业务逻辑接口
 * </pre>
 *
 * @author saysky
 * @date 2021/3/20
 */
public interface QuestionService extends BaseService<Question, Long> {

    /**
     * 添加点击数
     * @param questionId
     */
    void addView(Long questionId);

    /**
     * 点赞
     * @param questionId
     * @param user
     */
    void addLike(Long questionId, User user);


    /**
     * 收藏
     * @param questionId
     * @param user
     */
    void addMark(Long questionId, User user);

    /**
     * 删除收藏
     * @param questionId
     * @param user
     */
    void deleteMark(Long questionId, User user);
}
