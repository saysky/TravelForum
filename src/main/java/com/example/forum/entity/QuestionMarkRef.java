package com.example.forum.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.example.forum.common.base.BaseEntity;
import com.example.forum.util.RelativeDateFormat;
import lombok.Data;

/**
 * 收藏关联
 * @author saysky
 * @date 2021/3/20
 */

@Data
@TableName("question_mark_ref")
public class QuestionMarkRef extends BaseEntity {

    /**
     * 收藏人ID
     */
    private Long userId;

    /**
     * 帖子ID
     */
    private Long questionId;

    @TableField(exist = false)
    private Question question;

    @TableField(exist = false)
    private User user;


    /**
     * 创建时间
     */
    @TableField(exist = false)
    private String createTimeStr;

    public String getCreateTimeStr() {
        return RelativeDateFormat.format(getCreateTime());
    }
}
