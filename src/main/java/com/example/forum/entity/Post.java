package com.example.forum.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.example.forum.common.base.BaseEntity;
import com.example.forum.util.RelativeDateFormat;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author saysky
 */
@Data
@TableName("post")
public class Post extends BaseEntity {

    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 文章标题
     */
    private String postType;

    /**
     * 文章标题
     */
    private String postTitle;

    /**
     * 文章内容 html格式
     */
    private String postContent;

    /**
     * 文章摘要
     */
    private String postSummary;

    /**
     * 缩略图
     */
    private String postThumbnail;

    /**
     * 0 已发布
     * 1 草稿
     * 2 回收站
     */
    private Integer postStatus;

    /**
     * 文章点击数
     */
    private Long postViews;

    /**
     * 点赞点击数
     */
    private Long postLikes;

    /**
     * 回帖数量(冗余字段，加快查询速度)
     */
    private Integer commentSize;

    /**
     * 是否置顶
     */
    private Integer isSticky;

    /**
     * 是否推荐
     */
    private Integer isRecommend;

    /**
     * 分类ID
     */
    private Long cateId;


    /**
     * 发表用户 多对一
     */
    @TableField(exist = false)
    private User user;

    /**
     * 文章所属分类
     */
    @TableField(exist = false)
    private Category category;

    /**
     * 文章的回帖
     */
    @TableField(exist = false)
    private List<Comment> comments = new ArrayList<>();


    /**
     * 文章所属标签
     */
    @TableField(exist = false)
    private List<Tag> tagList = new ArrayList<>();

    /**
     * 更新时间
     */
    @TableField(exist = false)
    private String createTimeStr;

    public String getCreateTimeStr() {
        return RelativeDateFormat.format(getCreateTime());
    }

}