package com.example.forum.enums;

/**
 * 评论类型
 */
public enum CommentTypeEnum {

    /**
     * 文章
     */
    POST("post"),

    /**
     * 回帖
     */
    ANSWER("answer");


    private String code;

    CommentTypeEnum(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
