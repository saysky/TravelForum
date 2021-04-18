package com.example.forum.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.forum.entity.Answer;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author saysky
 * @date 2021/3/20
 */
@Mapper
public interface AnswerMapper extends BaseMapper<Answer> {

    /**
     * 更新记录回帖数
     *
     * @param answerId 记录Id
     */
    void resetCommentSize(Long answerId);

}
