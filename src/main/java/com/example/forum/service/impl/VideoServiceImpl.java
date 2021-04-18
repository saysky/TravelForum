package com.example.forum.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.forum.entity.Video;
import com.example.forum.mapper.VideoMapper;
import com.example.forum.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 言曌
 * @date 2021/4/17 1:35 下午
 */
@Service
public class VideoServiceImpl implements VideoService {

    @Autowired
    private VideoMapper videoMapper;

    @Override
    public BaseMapper<Video> getRepository() {
        return videoMapper;
    }

    @Override
    public QueryWrapper<Video> getQueryWrapper(Video video) {
        //对指定字段查询
        QueryWrapper<Video> queryWrapper = new QueryWrapper<>();
        if (video != null) {
            if (StrUtil.isNotBlank(video.getTitle())) {
                queryWrapper.like("title", video.getTitle());
            }

            if (video.getUserId() != null && video.getUserId() != -1) {
                queryWrapper.eq("user_id", video.getUserId());
            }
        }
        return queryWrapper;
    }
}
