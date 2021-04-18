package com.example.forum.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.example.forum.common.base.BaseEntity;
import com.example.forum.util.RelativeDateFormat;
import lombok.Data;

import java.util.List;

/**
 * @author saysky
 * @date 2021/3/20
 */
@Data
@TableName("answer")
public class Answer extends BaseEntity {

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 接受用户ID
     */
    private Long acceptUserId;

    /**
     * 帖子ID
     */
    private Long questionId;

    /**
     * 内容
     */
    private String content;

    /**
     * 摘要
     */
    private String summary;

    /**
     * 点赞数量
     */
    private Integer likeCount;

    /**
     * 点踩数量
     */
    private Integer dislikeCount;

    /**
     * 评论数
     */
    private Integer commentCount;


    /**
     * 帖子
     */
    @TableField(exist = false)
    private Question question;


    /**
     * 用户
     */
    @TableField(exist = false)
    private User user;

    /**
     * 评论
     */
    @TableField(exist = false)
    private List<Comment> commentList;
    /**
     * 创建时间
     */
    @TableField(exist = false)
    private String createTimeStr;

    public String getCreateTimeStr() {
        return RelativeDateFormat.format(getCreateTime());
    }
}
