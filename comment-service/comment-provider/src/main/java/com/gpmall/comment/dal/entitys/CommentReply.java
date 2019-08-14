package com.gpmall.comment.dal.entitys;

import java.io.Serializable;
import java.util.Date;

public class CommentReply implements Serializable {
    private String id;

    private String commentId;

    private String parentId;

    private String content;

    private Date replyTime;

    private String replyNick;

    private Long userId;

    private Boolean isDeleted;

    private Date deletionTime;

    private Long deletionUserId;

    private static final long serialVersionUID = 1L;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId == null ? null : commentId.trim();
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId == null ? null : parentId.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public Date getReplyTime() {
        return replyTime;
    }

    public void setReplyTime(Date replyTime) {
        this.replyTime = replyTime;
    }

    public String getReplyNick() {
        return replyNick;
    }

    public void setReplyNick(String replyNick) {
        this.replyNick = replyNick == null ? null : replyNick.trim();
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Date getDeletionTime() {
        return deletionTime;
    }

    public void setDeletionTime(Date deletionTime) {
        this.deletionTime = deletionTime;
    }

    public Long getDeletionUserId() {
        return deletionUserId;
    }

    public void setDeletionUserId(Long deletionUserId) {
        this.deletionUserId = deletionUserId;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", commentId=").append(commentId);
        sb.append(", parentId=").append(parentId);
        sb.append(", content=").append(content);
        sb.append(", replyTime=").append(replyTime);
        sb.append(", replyNick=").append(replyNick);
        sb.append(", userId=").append(userId);
        sb.append(", isDeleted=").append(isDeleted);
        sb.append(", deletionTime=").append(deletionTime);
        sb.append(", deletionUserId=").append(deletionUserId);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}