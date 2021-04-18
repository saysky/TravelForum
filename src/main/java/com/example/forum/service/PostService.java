package com.example.forum.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.forum.common.base.BaseService;
import com.example.forum.dto.PostQueryCondition;
import com.example.forum.entity.Post;

/**
 * <pre>
 *     记录/页面业务逻辑接口
 * </pre>
 */
public interface PostService extends BaseService<Post, Long> {

    /**
     * 修改记录阅读量
     *
     * @param postId 记录Id
     * @return 记录点击数
     */
    void updatePostView(Long postId);

    /**
     * 更新记录回帖数
     *
     * @param postId 记录Id
     */
    void resetCommentSize(Long postId);


    /**
     * 根据条件获得列表
     * @param condition
     * @return
     */
    Page<Post> findPostByCondition(PostQueryCondition condition, Page<Post> page);




}
