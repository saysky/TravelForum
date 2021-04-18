package com.example.forum.enums;

/**
 * 分类类型
 */
public enum CategoryTypeEnum {

    /**
     * 文章
     */
    POST("post"),

    /**
     * 帖子
     */
    QUESTION("question");


    private String code;

    CategoryTypeEnum(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
