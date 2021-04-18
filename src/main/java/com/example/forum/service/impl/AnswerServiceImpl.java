package com.example.forum.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.forum.entity.Answer;
import com.example.forum.entity.Question;
import com.example.forum.enums.QuestionNoticeTypeEnum;
import com.example.forum.mapper.AnswerMapper;
import com.example.forum.mapper.QuestionMapper;
import com.example.forum.service.AnswerService;
import com.example.forum.service.DeleteService;
import com.example.forum.service.QuestionNoticeRefService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * <pre>
 *     回帖业务逻辑实现类
 * </pre>
 */
@Service
public class AnswerServiceImpl implements AnswerService {


    @Autowired
    private AnswerMapper answerMapper;

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private DeleteService deleteService;

    @Autowired
    private QuestionNoticeRefService questionNoticeRefService;

    @Override
    public BaseMapper<Answer> getRepository() {
        return answerMapper;
    }

    @Override
    public QueryWrapper<Answer> getQueryWrapper(Answer answer) {
        //对指定字段查询
        QueryWrapper<Answer> queryWrapper = new QueryWrapper<>();
        if (answer != null) {
            if (answer.getUserId() != null && answer.getUserId() != -1) {
                queryWrapper.eq("user_id", answer.getUserId());
            }
            if (answer.getAcceptUserId() != null && answer.getAcceptUserId() != -1) {
                queryWrapper.eq("accept_user_id", answer.getAcceptUserId());
            }
            if (answer.getQuestionId() != null && answer.getQuestionId() != -1) {
                queryWrapper.eq("question_id", answer.getQuestionId());
            }
        }
        return queryWrapper;
    }

    @Override
    public Answer insertOrUpdate(Answer entity) {
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
        deleteService.deleteAnswer(id);
    }

    @Override
    public List<Answer> findAll() {
        List<Answer> answerList = answerMapper.selectList(null);
        return answerList;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Answer insert(Answer entity) {
        // 添加
        answerMapper.insert(entity);

        // 2.修改帖子的回帖数
        Question question = questionMapper.selectById(entity.getQuestionId());
        if (question != null) {
            QueryWrapper<Answer> answerQueryWrapper = new QueryWrapper<>();
            answerQueryWrapper.eq("question_id", question.getId());
            List<Answer> answerList = answerMapper.selectList(answerQueryWrapper);
            question.setAnswerCount(answerList.size());
            questionMapper.updateById(question);
        }

        // 通知
        questionNoticeRefService.insert(entity.getUserId(), entity.getQuestionId(), QuestionNoticeTypeEnum.ANSWER);
        return entity;
    }

    @Override
    public void resetCommentSize(Long answerId) {
        answerMapper.resetCommentSize(answerId);
    }
}
