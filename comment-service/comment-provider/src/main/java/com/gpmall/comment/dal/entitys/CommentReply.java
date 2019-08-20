package com.gpmall.comment.dal.entitys;

import lombok.Data;

import java.util.Date;
import javax.persistence.*;

@Table(name = "tb_comment_reply")
@Data
public class CommentReply {
    /**
     * 评价回复id
     */
    @Id
    private String id;

    /**
     * 商品评价id
     */
    @Column(name = "comment_id")
    private String commentId;

    /**
     * 评价回复自关联id(针对回复的回复)
     */
    @Column(name = "parent_id")
    private String parentId;

    /**
     * 回复意见
     */
    private String content;

    /**
     * 回复时间
     */
    @Column(name = "reply_time")
    private Date replyTime;

    /**
     * 回复人昵称
     */
    @Column(name = "reply_nick")
    private String replyNick;

    /**
     * 回复人用户id
     */
    @Column(name = "user_id")
    private Long userId;

    /**
     * 是否删除
     */
    @Column(name = "is_deleted")
    private Boolean isDeleted;

    /**
     * 删除时间
     */
    @Column(name = "deletion_time")
    private Date deletionTime;

    /**
     * 删除用户id
     */
    @Column(name = "deletion_user_id")
    private Long deletionUserId;

    /**
     * 获取评价回复id
     *
     * @return id - 评价回复id
     */
    public String getId() {
        return id;
    }

    /**
     * 设置评价回复id
     *
     * @param id 评价回复id
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * 获取商品评价id
     *
     * @return comment_id - 商品评价id
     */
    public String getCommentId() {
        return commentId;
    }

    /**
     * 设置商品评价id
     *
     * @param commentId 商品评价id
     */
    public void setCommentId(String commentId) {
        this.commentId = commentId == null ? null : commentId.trim();
    }

    /**
     * 获取评价回复自关联id(针对回复的回复)
     *
     * @return parent_id - 评价回复自关联id(针对回复的回复)
     */
    public String getParentId() {
        return parentId;
    }

    /**
     * 设置评价回复自关联id(针对回复的回复)
     *
     * @param parentId 评价回复自关联id(针对回复的回复)
     */
    public void setParentId(String parentId) {
        this.parentId = parentId == null ? null : parentId.trim();
    }

    /**
     * 获取回复意见
     *
     * @return content - 回复意见
     */
    public String getContent() {
        return content;
    }

    /**
     * 设置回复意见
     *
     * @param content 回复意见
     */
    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    /**
     * 获取回复时间
     *
     * @return reply_time - 回复时间
     */
    public Date getReplyTime() {
        return replyTime;
    }

    /**
     * 设置回复时间
     *
     * @param replyTime 回复时间
     */
    public void setReplyTime(Date replyTime) {
        this.replyTime = replyTime;
    }

    /**
     * 获取回复人昵称
     *
     * @return reply_nick - 回复人昵称
     */
    public String getReplyNick() {
        return replyNick;
    }

    /**
     * 设置回复人昵称
     *
     * @param replyNick 回复人昵称
     */
    public void setReplyNick(String replyNick) {
        this.replyNick = replyNick == null ? null : replyNick.trim();
    }

    /**
     * 获取回复人用户id
     *
     * @return user_id - 回复人用户id
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * 设置回复人用户id
     *
     * @param userId 回复人用户id
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * 获取是否删除
     *
     * @return is_deleted - 是否删除
     */
    public Boolean getIsDeleted() {
        return isDeleted;
    }

    /**
     * 设置是否删除
     *
     * @param isDeleted 是否删除
     */
    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    /**
     * 获取删除时间
     *
     * @return deletion_time - 删除时间
     */
    public Date getDeletionTime() {
        return deletionTime;
    }

    /**
     * 设置删除时间
     *
     * @param deletionTime 删除时间
     */
    public void setDeletionTime(Date deletionTime) {
        this.deletionTime = deletionTime;
    }

    /**
     * 获取删除用户id
     *
     * @return deletion_user_id - 删除用户id
     */
    public Long getDeletionUserId() {
        return deletionUserId;
    }

    /**
     * 设置删除用户id
     *
     * @param deletionUserId 删除用户id
     */
    public void setDeletionUserId(Long deletionUserId) {
        this.deletionUserId = deletionUserId;
    }
}