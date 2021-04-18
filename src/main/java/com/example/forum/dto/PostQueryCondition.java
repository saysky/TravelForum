package com.example.forum.dto;

import lombok.Data;

/**
 * @author saysky
 * @date 2021/3/20
 */
@Data
public class PostQueryCondition {

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 分类ID
     */
    private Long cateId;

    /**
     * 关键字
     */
    private String keywords;

    /**
     * 标签ID
     */
    private Long tagId;
}
