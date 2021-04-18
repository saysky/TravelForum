package com.example.forum.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.forum.entity.Follow;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author saysky
 */
@Mapper
public interface FollowMapper extends BaseMapper<Follow> {

    /**
     * 获得某个用户的粉丝用户ID
     * @param userId
     * @return
     */
    List<Long> getFansUserIds(Long userId);
}

