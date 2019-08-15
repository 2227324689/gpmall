package com.gpmall.comment.dal.entitys;

import java.io.Serializable;
import java.util.Date;

public class Comment implements Serializable {
    private String id;

    private String orderId;

    private String itemId;

    private Byte star;

    private Byte type;

    private Boolean isAnoymous;

    private String content;

    private String buyerNick;

    private Date commentTime;

    private Boolean isPublic;

    private Boolean isValid;

    private Long validationUserId;

    private Date validationTime;

    private String validationSuggestion;

    private Boolean isTop;

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

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId == null ? null : orderId.trim();
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId == null ? null : itemId.trim();
    }

    public Byte getStar() {
        return star;
    }

    public void setStar(Byte star) {
        this.star = star;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public Boolean getIsAnoymous() {
        return isAnoymous;
    }

    public void setIsAnoymous(Boolean isAnoymous) {
        this.isAnoymous = isAnoymous;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public String getBuyerNick() {
        return buyerNick;
    }

    public void setBuyerNick(String buyerNick) {
        this.buyerNick = buyerNick == null ? null : buyerNick.trim();
    }

    public Date getCommentTime() {
        return commentTime;
    }

    public void setCommentTime(Date commentTime) {
        this.commentTime = commentTime;
    }

    public Boolean getIsPublic() {
        return isPublic;
    }

    public void setIsPublic(Boolean isPublic) {
        this.isPublic = isPublic;
    }

    public Boolean getIsValid() {
        return isValid;
    }

    public void setIsValid(Boolean isValid) {
        this.isValid = isValid;
    }

    public Long getValidationUserId() {
        return validationUserId;
    }

    public void setValidationUserId(Long validationUserId) {
        this.validationUserId = validationUserId;
    }

    public Date getValidationTime() {
        return validationTime;
    }

    public void setValidationTime(Date validationTime) {
        this.validationTime = validationTime;
    }

    public String getValidationSuggestion() {
        return validationSuggestion;
    }

    public void setValidationSuggestion(String validationSuggestion) {
        this.validationSuggestion = validationSuggestion == null ? null : validationSuggestion.trim();
    }

    public Boolean getIsTop() {
        return isTop;
    }

    public void setIsTop(Boolean isTop) {
        this.isTop = isTop;
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
        sb.append(", orderId=").append(orderId);
        sb.append(", itemId=").append(itemId);
        sb.append(", star=").append(star);
        sb.append(", type=").append(type);
        sb.append(", isAnoymous=").append(isAnoymous);
        sb.append(", content=").append(content);
        sb.append(", buyerNick=").append(buyerNick);
        sb.append(", commentTime=").append(commentTime);
        sb.append(", isPublic=").append(isPublic);
        sb.append(", isValid=").append(isValid);
        sb.append(", validationUserId=").append(validationUserId);
        sb.append(", validationTime=").append(validationTime);
        sb.append(", validationSuggestion=").append(validationSuggestion);
        sb.append(", isTop=").append(isTop);
        sb.append(", userId=").append(userId);
        sb.append(", isDeleted=").append(isDeleted);
        sb.append(", deletionTime=").append(deletionTime);
        sb.append(", deletionUserId=").append(deletionUserId);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}