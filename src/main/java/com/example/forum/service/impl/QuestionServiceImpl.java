package com.example.forum.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.forum.dto.JsonResult;
import com.example.forum.entity.*;
import com.example.forum.enums.QuestionNoticeTypeEnum;
import com.example.forum.exception.MyBusinessException;
import com.example.forum.mapper.*;
import com.example.forum.service.DeleteService;
import com.example.forum.service.QuestionNoticeRefService;
import com.example.forum.service.QuestionService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * <pre>
 *     帖子业务逻辑实现类
 * </pre>
 */
@Service
public class QuestionServiceImpl implements QuestionService {


    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private DeleteService deleteService;

    @Autowired
    private QuestionMarkRefMapper questionMarkRefMapper;

    @Autowired
    private QuestionLikeRefMapper questionLikeRefMapper;

    @Autowired
    private FollowMapper followMapper;

    @Autowired
    private QuestionNoticeRefService questionNoticeRefService;

    @Override
    public BaseMapper<Question> getRepository() {
        return questionMapper;
    }

    @Override
    public QueryWrapper<Question> getQueryWrapper(Question question) {
        //对指定字段查询
        QueryWrapper<Question> queryWrapper = new QueryWrapper<>();
        if (question != null) {
            if (question.getUserId() != null) {
                queryWrapper.eq("user_id", question.getUserId());
            }
            if (question.getCateId() != null) {
                queryWrapper.eq("cate_id", question.getCateId());
            }
            if (StringUtils.isNotEmpty(question.getTitle())) {
                queryWrapper.eq("title", question.getTitle());
            }
        }
        return queryWrapper;
    }

    @Override
    public Question insertOrUpdate(Question entity) {
        if (entity.getId() == null) {
            insert(entity);
        } else {
            update(entity);
        }
        return entity;
    }

    @Override
    public void delete(Long id) {
        deleteService.deleteQuestion(id);
    }

    @Override
    public List<Question> findAll() {
        List<Question> questionList = questionMapper.selectList(null);
        return questionList;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Question insert(Question entity) {
        // 发布帖子
        questionMapper.insert(entity);

        // 给关注者通知
        questionNoticeRefService.insert(entity.getUserId(), entity.getId(), QuestionNoticeTypeEnum.PUBLISH);
        return entity;
    }

    @Override
    public void addView(Long questionId) {
        Question question = questionMapper.selectById(questionId);
        if (question != null) {
            question.setViewCount(question.getViewCount() + 1);
            questionMapper.updateById(question);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addLike(Long questionId, User user) {
        QueryWrapper<QuestionLikeRef> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", user.getId());
        queryWrapper.eq("question_id", questionId);
        List<QuestionLikeRef> questionLikeRefs = questionLikeRefMapper.selectList(queryWrapper);
        if (questionLikeRefs != null && questionLikeRefs.size() > 0) {
            throw new MyBusinessException("您已经点赞过了");
        }


        // 更新
        Question question = questionMapper.selectById(questionId);
        if (question == null) {
            throw new MyBusinessException("帖子不存在");
        }
        question.setLikeCount(question.getLikeCount() + 1);
        questionMapper.updateById(question);

        // 添加点赞关联
        QuestionLikeRef questionLikeRef = new QuestionLikeRef();
        questionLikeRef.setUserId(user.getId());
        questionLikeRef.setQuestionId(questionId);
        questionLikeRef.setCreateTime(new Date());
        questionLikeRef.setUpdateTime(new Date());
        questionLikeRef.setCreateBy(user.getUserName());
        questionLikeRef.setUpdateBy(user.getUserName());
        questionLikeRefMapper.insert(questionLikeRef);

        // 给关注者通知
        questionNoticeRefService.insert(user.getId(), questionId, QuestionNoticeTypeEnum.LIKE);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addMark(Long questionId, User user) {

        QueryWrapper<QuestionMarkRef> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", user.getId());
        queryWrapper.eq("question_id", questionId);
        List<QuestionMarkRef> questionMarkRefs = questionMarkRefMapper.selectList(queryWrapper);
        if (questionMarkRefs != null && questionMarkRefs.size() > 0) {
            throw new MyBusinessException("您已经收藏过了");
        }

        // 更新
        Question question = questionMapper.selectById(questionId);
        if (question == null) {
            throw new MyBusinessException("帖子不存在");
        }
        question.setMarkCount(question.getMarkCount() + 1);
        questionMapper.updateById(question);

        // 添加收藏关联
        QuestionMarkRef questionMarkRef = new QuestionMarkRef();
        questionMarkRef.setUserId(user.getId());
        questionMarkRef.setQuestionId(questionId);
        questionMarkRef.setCreateTime(new Date());
        questionMarkRef.setUpdateTime(new Date());
        questionMarkRef.setCreateBy(user.getUserName());
        questionMarkRef.setUpdateBy(user.getUserName());
        questionMarkRefMapper.insert(questionMarkRef);

        // 通知
        questionNoticeRefService.insert(user.getId(), questionId, QuestionNoticeTypeEnum.MARK);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteMark(Long questionId, User user) {
        // 删除关联
        QueryWrapper<QuestionMarkRef> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", user.getId());
        queryWrapper.eq("question_id", questionId);
        questionMarkRefMapper.delete(queryWrapper);

        // 更新
        Question question = questionMapper.selectById(questionId);
        if (question == null) {
            throw new MyBusinessException("帖子不存在");
        }
        question.setMarkCount(question.getMarkCount() - 1);
        questionMapper.updateById(question);
    }
}
