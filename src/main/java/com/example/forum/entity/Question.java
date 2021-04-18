package com.example.forum.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.example.forum.common.base.BaseEntity;
import com.example.forum.util.RelativeDateFormat;
import lombok.Data;

/**
 * @author saysky
 * @date 2021/3/20
 */
@Data
@TableName("question")
public class Question extends BaseEntity {

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 标题
     */
    private String title;

    /**
     * 内容
     */
    private String content;

    /**
     * 摘要
     */
    private String summary;

    /**
     * 回帖数
     */
    private Integer answerCount;


    /**
     * 点赞数
     */
    private Integer likeCount;


    /**
     * 收藏数
     */
    private Integer markCount;


    /**
     * 点击数
     */
    private Integer viewCount;

    /**
     * 分类ID
     */
    private Long cateId;

    @TableField(exist = false)
    private User user;

    @TableField(exist = false)
    private Category category;


    /**
     * 创建时间
     */
    @TableField(exist = false)
    private String createTimeStr;

    public String getCreateTimeStr() {
        return RelativeDateFormat.format(getCreateTime());
    }
}
