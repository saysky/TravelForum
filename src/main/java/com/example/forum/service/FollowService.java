package com.example.forum.service;


import com.example.forum.common.base.BaseService;
import com.example.forum.entity.Follow;
import com.example.forum.entity.User;


/**
 * <pre>
 *    关注业务逻辑接口
 * </pre>
 *
 */
public interface FollowService extends BaseService<Follow, Long> {


    /**
     * 根据用户ID和被关注用户ID查询
     * @param userId
     * @param acceptUserId
     * @return
     */
    Follow findByUserIdAndAcceptUserId(Long userId, Long acceptUserId);

    /**
     * 关注用户
     * @param user
     * @param acceptUserId
     * @return
     */
    void follow(User user, Long acceptUserId);

    /**
     * 取关用户
     * @param user
     * @param acceptUserId
     * @return
     */
    void unfollow(User user, Long acceptUserId);

}
