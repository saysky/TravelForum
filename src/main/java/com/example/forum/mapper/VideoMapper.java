package com.example.forum.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.forum.entity.Video;
import org.apache.ibatis.annotations.Mapper;

/**
 * 视频mapper
 * @author 言曌
 * @date 2021/4/17 1:35 下午
 */
@Mapper
public interface VideoMapper extends BaseMapper<Video> {
}
