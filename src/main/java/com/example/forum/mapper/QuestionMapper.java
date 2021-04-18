package com.example.forum.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.forum.entity.Question;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author saysky
 * @date 2021/3/20
 */
@Mapper
public interface QuestionMapper extends BaseMapper<Question> {

}
