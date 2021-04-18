package com.example.forum.enums;

/**
 * 帖子通知类型
 */
public enum QuestionNoticeTypeEnum {

    /**
     * 发布
     */
    PUBLISH("publish"),

    /**
     * 回帖
     */
    ANSWER("answer"),

    /**
     * 点赞
     */
    LIKE("like"),


    /**
     * 收藏
     */
    MARK("mark"),
    ;


    private String code;

    QuestionNoticeTypeEnum(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
