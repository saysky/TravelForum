package com.example.forum.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.example.forum.common.base.BaseEntity;
import com.example.forum.util.RelativeDateFormat;
import lombok.Data;

/**
 * 帖子通知关联
 *
 * @author saysky
 * @date 2021/3/20
 */

@Data
@TableName("question_notice_ref")
public class QuestionNoticeRef extends BaseEntity {

    /**
     * 操作者用户ID
     */
    private Long userId;

    /**
     * 通知用户ID
     */
    private Long acceptUserId;

    /**
     * 类型：like点赞帖子，mark收藏帖子，publish发布帖子，answer回帖帖子
     */
    private String type;

    /**
     * 帖子ID
     */
    private Long questionId;


    @TableField(exist = false)
    private Question question;

    @TableField(exist = false)
    private User user;

    @TableField(exist = false)
    private User acceptUser;


    /**
     * 创建时间
     */
    @TableField(exist = false)
    private String createTimeStr;

    public String getCreateTimeStr() {
        return RelativeDateFormat.format(getCreateTime());
    }
}
