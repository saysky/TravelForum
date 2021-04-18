package com.example.forum.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.forum.entity.QuestionMarkRef;
import com.example.forum.mapper.QuestionMarkRefMapper;
import com.example.forum.service.QuestionMarkRefService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * <pre>
 *     帖子收藏关联逻辑实现类
 * </pre>
 */
@Service
public class QuestionMarkRefServiceImpl implements QuestionMarkRefService {


    @Autowired
    private QuestionMarkRefMapper questionMarkRefMapper;

    @Override
    public BaseMapper<QuestionMarkRef> getRepository() {
        return questionMarkRefMapper;
    }

    @Override
    public QueryWrapper<QuestionMarkRef> getQueryWrapper(QuestionMarkRef questionMarkRef) {
        //对指定字段查询
        QueryWrapper<QuestionMarkRef> queryWrapper = new QueryWrapper<>();
        if (questionMarkRef != null) {
            if (questionMarkRef.getUserId() != null && questionMarkRef.getUserId() != -1) {
                queryWrapper.eq("user_id", questionMarkRef.getUserId());
            }
            if (questionMarkRef.getQuestionId() != null && questionMarkRef.getQuestionId() != -1) {
                queryWrapper.eq("question_id", questionMarkRef.getQuestionId());
            }
        }
        return queryWrapper;
    }

    @Override
    public QuestionMarkRef insertOrUpdate(QuestionMarkRef entity) {
        if (entity.getId() == null) {
            entity.setCreateTime(new Date());
            entity.setUpdateTime(new Date());
            insert(entity);
        } else {
            entity.setUpdateTime(new Date());
            update(entity);
        }
        return entity;
    }

    @Override
    public void delete(Long id) {
        questionMarkRefMapper.deleteById(id);
    }

    @Override
    public List<QuestionMarkRef> findAll() {
        List<QuestionMarkRef> questionMarkRefList = questionMarkRefMapper.selectList(null);
        return questionMarkRefList;
    }

}
