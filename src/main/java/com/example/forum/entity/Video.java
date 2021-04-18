package com.example.forum.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.example.forum.common.base.BaseEntity;
import com.example.forum.util.RelativeDateFormat;
import lombok.Data;

/**
 * 视频
 *
 * @author 言曌
 * @date 2021/4/17 12:51 下午
 */
@Data
@TableName("video")
public class Video extends BaseEntity {

    /**
     * 标题
     */
    private String title;

    /**
     * 内容
     */
    private String content;

    /**
     * 图片URL
     */
    private String imgUrl;

    /**
     * 视频URL
     */
    private String videoUrl;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 访问数
     */
    private Integer videoViews;

    /**
     * 用户
     */
    @TableField(exist = false)
    private User user;

    /**
     * 更新时间
     */
    @TableField(exist = false)
    private String createTimeStr;

    public String getCreateTimeStr() {
        return RelativeDateFormat.format(getCreateTime());
    }

}
