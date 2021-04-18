package com.example.forum.service;


import com.example.forum.common.base.BaseService;
import com.example.forum.entity.QuestionNoticeRef;
import com.example.forum.enums.QuestionNoticeTypeEnum;

/**
 * <pre>
 *     帖子通知业务逻辑接口
 * </pre>
 */
public interface QuestionNoticeRefService extends BaseService<QuestionNoticeRef, Long> {

    /**
     * 添加
     * @param userId
     * @param questionId
     * @param questionNoticeTypeEnum
     */
    void insert(Long userId, Long questionId, QuestionNoticeTypeEnum questionNoticeTypeEnum);
}
