package com.example.forum.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.forum.dto.PostQueryCondition;
import com.example.forum.dto.QueryCondition;
import com.example.forum.entity.*;
import com.example.forum.enums.CommentTypeEnum;
import com.example.forum.mapper.*;
import com.example.forum.service.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.ListUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * <pre>
 *     文章业务逻辑实现类
 * </pre>
 */
@Service
@Slf4j
public class PostServiceImpl implements PostService {

    @Autowired
    private DeleteService deleteService;

    @Autowired
    private PostMapper postMapper;

    @Autowired
    private PostTagRefMapper postTagRefMapper;


    @Override
    @Async
    public void updatePostView(Long postId) {
        postMapper.incrPostViews(postId);
    }


    @Override
    public void resetCommentSize(Long postId) {
        postMapper.resetCommentSize(postId);
    }


    @Override
    public Page<Post> findPostByCondition(PostQueryCondition condition, Page<Post> page) {
        List<Post> postList = postMapper.findPostByCondition(condition, page);
        return page.setRecords(postList);
    }


    @Override
    public BaseMapper<Post> getRepository() {
        return postMapper;
    }

    @Override
    public Post insert(Post post) {
        post.setPostViews(0L);
        post.setCommentSize(0);
        post.setPostLikes(0L);
        postMapper.insert(post);
        //添加记录标签关系
        if (post.getTagList() != null) {
            for (int i = 0; i < post.getTagList().size(); i++) {
                postTagRefMapper.insert(new PostTagRef(post.getId(), post.getTagList().get(i).getId()));
            }
        }
        return post;
    }

    @Override
    public Post update(Post post) {
        postMapper.updateById(post);
        if (post.getTagList() != null && post.getTagList().size() != 0) {
            //删除标签和记录关联
            postTagRefMapper.deleteByPostId(post.getId());
            //添加标签和记录关联
            for (int i = 0; i < post.getTagList().size(); i++) {
                PostTagRef postTagRef = new PostTagRef(post.getId(), post.getTagList().get(i).getId());
                postTagRefMapper.insert(postTagRef);
            }
        }
        return post;
    }

    @Override
    public void delete(Long postId) {
        deleteService.deletePost(postId);
    }

    @Override
    public QueryWrapper<Post> getQueryWrapper(Post post) {
        //对指定字段查询
        QueryWrapper<Post> queryWrapper = new QueryWrapper<>();
        if (post != null) {
            if (StrUtil.isNotBlank(post.getPostTitle())) {
                queryWrapper.like("post_title", post.getPostTitle());
            }
            if (StrUtil.isNotBlank(post.getPostContent())) {
                queryWrapper.like("post_content", post.getPostContent());
            }
            if (post.getUserId() != null && post.getUserId() != -1) {
                queryWrapper.eq("user_id", post.getUserId());
            }
            if (post.getCateId() != null && post.getCateId() != -1) {
                queryWrapper.eq("cate_id", post.getCateId());
            }
            if (post.getPostStatus() != null && post.getPostStatus() != -1) {
                queryWrapper.eq("post_status", post.getPostStatus());
            }
            if (StringUtils.isNotEmpty(post.getPostType())) {
                queryWrapper.eq("post_type", post.getPostType());
            }
        }
        return queryWrapper;
    }

    @Override
    public Post insertOrUpdate(Post post) {
        if (post.getId() == null) {
            insert(post);
        } else {
            update(post);
        }
        return post;
    }

}

