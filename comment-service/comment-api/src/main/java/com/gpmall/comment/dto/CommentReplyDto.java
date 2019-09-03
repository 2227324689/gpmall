package com.gpmall.comment.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author heps
 * @date 2019/8/18 17:16
 * 商品评价回复dto
 */
@Data
public class CommentReplyDto implements Serializable {

    /**
     * 评价回复id
     */
    private String id;

    /**
     * 商品评价id
     */
    private String commentId;

    /**
     * 评价回复自关联id(针对回复的回复)
     */
    private String parentId;

    /**
     * 回复意见
     */
    private String content;

    /**
     * 回复时间
     */
    private Date replyTime;

    /**
     * 回复人昵称
     */
    private String replyNick;

    /**
     * 回复人用户id
     */
    private Long userId;

    /**
     * 是否删除
     */
    private Boolean isDeleted;

    /**
     * 删除时间
     */
    private Date deletionTime;

    /**
     * 删除用户id
     */
    private Long deletionUserId;
}
