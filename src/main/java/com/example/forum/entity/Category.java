package com.example.forum.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.example.forum.common.base.BaseEntity;
import lombok.Data;

/**
 * <pre>
 *     文章分类
 * </pre>
 *
 * @author saysky
 * @date 2021/3/20
 */
@Data
@TableName("category")
public class Category extends BaseEntity {

    /**
     * 分类名称
     */
    private String cateName;

    /**
     * 分类排序号
     */
    private Integer cateSort;

    /**
     * 分类描述
     */
    private String cateDesc;

    /**
     * 类型
     */
    private String cateType;
}
