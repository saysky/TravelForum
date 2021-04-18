package com.example.forum.enums;

/**
 * <pre>
 *     文章状态enum
 * </pre>
 *
 * @author saysky
 * @date 2021/3/20
 */
public enum PostStatusEnum {

    /**
     * 已发布
     */
    PUBLISHED(0),

    /**
     * 草稿
     */
    DRAFT(1),

    /**
     * 回收站
     */
    RECYCLE(2),

    /**
     * 待审核
     */
    CHECKING(3);

    private Integer code;

    PostStatusEnum(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

}
