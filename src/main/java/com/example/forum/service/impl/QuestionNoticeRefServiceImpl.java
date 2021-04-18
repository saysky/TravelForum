package com.example.forum.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.forum.entity.QuestionNoticeRef;
import com.example.forum.enums.QuestionNoticeTypeEnum;
import com.example.forum.mapper.FollowMapper;
import com.example.forum.mapper.QuestionNoticeRefMapper;
import com.example.forum.service.QuestionNoticeRefService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * <pre>
 *     帖子通知业务逻辑实现类
 * </pre>
 *
 */
@Service
public class QuestionNoticeRefServiceImpl implements QuestionNoticeRefService {

    @Autowired
    private FollowMapper followMapper;

    @Autowired
    private QuestionNoticeRefMapper questionNoticeRefMapper;

    @Override
    public BaseMapper<QuestionNoticeRef> getRepository() {
        return questionNoticeRefMapper;
    }

    @Override
    public QueryWrapper<QuestionNoticeRef> getQueryWrapper(QuestionNoticeRef questionNoticeRef) {
        //对指定字段查询
        QueryWrapper<QuestionNoticeRef> queryWrapper = new QueryWrapper<>();
        if (questionNoticeRef != null) {
            if (questionNoticeRef.getUserId() != null && questionNoticeRef.getUserId() != -1) {
                queryWrapper.eq("user_id", questionNoticeRef.getUserId());
            }
            if (questionNoticeRef.getAcceptUserId() != null && questionNoticeRef.getAcceptUserId() != -1) {
                queryWrapper.eq("accept_user_id", questionNoticeRef.getAcceptUserId());
            }
        }
        return queryWrapper;
    }

    @Override
    public QuestionNoticeRef insertOrUpdate(QuestionNoticeRef entity) {
        if (entity.getId() == null) {
            insert(entity);
        } else {
            update(entity);
        }
        return entity;
    }

    @Override
    public void delete(Long id) {
        questionNoticeRefMapper.deleteById(id);
    }

    @Override
    public List<QuestionNoticeRef> findAll() {
        List<QuestionNoticeRef> questionNoticeRefList = questionNoticeRefMapper.selectList(null);
        return questionNoticeRefList;
    }

    @Override
    public void insert(Long userId, Long questionId, QuestionNoticeTypeEnum questionNoticeTypeEnum) {
        // 给关注者通知
        List<Long> fansUserIds = followMapper.getFansUserIds(userId);
        for(Long fansUserId : fansUserIds) {
            QuestionNoticeRef questionNoticeRef = new QuestionNoticeRef();
            questionNoticeRef.setUserId(userId);
            questionNoticeRef.setAcceptUserId(fansUserId);
            questionNoticeRef.setQuestionId(questionId);
            questionNoticeRef.setType(questionNoticeTypeEnum.getCode());
            questionNoticeRef.setCreateTime(new Date());
            questionNoticeRef.setUpdateTime(new Date());
            questionNoticeRefMapper.insert(questionNoticeRef);
        }
    }
}
