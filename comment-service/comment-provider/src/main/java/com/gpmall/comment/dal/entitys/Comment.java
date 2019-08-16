package com.gpmall.comment.dal.entitys;

import lombok.Data;

import java.util.Date;
import javax.persistence.*;

@Table(name = "tb_comment")
@Data
public class Comment {
    /**
     * 商品评论主键
     */
    @Id
    private String id;

    /**
     * 订单id
     */
    @Column(name = "order_id")
    private String orderId;

    /**
     * 商品id
     */
    @Column(name = "item_id")
    private String itemId;

    /**
     * 星级
     */
    private Byte star;

    /**
     * 类型: 1好评 2中评 3差评
     */
    private Byte type;

    /**
     * 是否匿名评价
     */
    @Column(name = "is_anoymous")
    private Boolean isAnoymous;

    /**
     * 评价内容
     */
    private String content;

    /**
     * 买家昵称
     */
    @Column(name = "buyer_nick")
    private String buyerNick;

    /**
     * 评价时间
     */
    @Column(name = "comment_time")
    private Date commentTime;

    /**
     * 是否公开
     */
    @Column(name = "is_public")
    private Boolean isPublic;

    /**
     * 是否通过审核
     */
    @Column(name = "is_valid")
    private Boolean isValid;

    /**
     * 审核人id
     */
    @Column(name = "validation_user_id")
    private Long validationUserId;

    /**
     * 审核时间
     */
    @Column(name = "validation_time")
    private Date validationTime;

    /**
     * 审核意见
     */
    @Column(name = "validation_suggestion")
    private String validationSuggestion;

    /**
     * 是否置顶
     */
    @Column(name = "is_top")
    private Boolean isTop;

    /**
     * 评论用户id
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
     * 获取商品评论主键
     *
     * @return id - 商品评论主键
     */
    public String getId() {
        return id;
    }

    /**
     * 设置商品评论主键
     *
     * @param id 商品评论主键
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * 获取订单id
     *
     * @return order_id - 订单id
     */
    public String getOrderId() {
        return orderId;
    }

    /**
     * 设置订单id
     *
     * @param orderId 订单id
     */
    public void setOrderId(String orderId) {
        this.orderId = orderId == null ? null : orderId.trim();
    }

    /**
     * 获取商品id
     *
     * @return item_id - 商品id
     */
    public String getItemId() {
        return itemId;
    }

    /**
     * 设置商品id
     *
     * @param itemId 商品id
     */
    public void setItemId(String itemId) {
        this.itemId = itemId == null ? null : itemId.trim();
    }

    /**
     * 获取星级
     *
     * @return star - 星级
     */
    public Byte getStar() {
        return star;
    }

    /**
     * 设置星级
     *
     * @param star 星级
     */
    public void setStar(Byte star) {
        this.star = star;
    }

    /**
     * 获取类型: 1好评 2中评 3差评
     *
     * @return type - 类型: 1好评 2中评 3差评
     */
    public Byte getType() {
        return type;
    }

    /**
     * 设置类型: 1好评 2中评 3差评
     *
     * @param type 类型: 1好评 2中评 3差评
     */
    public void setType(Byte type) {
        this.type = type;
    }

    /**
     * 获取是否匿名评价
     *
     * @return is_anoymous - 是否匿名评价
     */
    public Boolean getIsAnoymous() {
        return isAnoymous;
    }

    /**
     * 设置是否匿名评价
     *
     * @param isAnoymous 是否匿名评价
     */
    public void setIsAnoymous(Boolean isAnoymous) {
        this.isAnoymous = isAnoymous;
    }

    /**
     * 获取评价内容
     *
     * @return content - 评价内容
     */
    public String getContent() {
        return content;
    }

    /**
     * 设置评价内容
     *
     * @param content 评价内容
     */
    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    /**
     * 获取买家昵称
     *
     * @return buyer_nick - 买家昵称
     */
    public String getBuyerNick() {
        return buyerNick;
    }

    /**
     * 设置买家昵称
     *
     * @param buyerNick 买家昵称
     */
    public void setBuyerNick(String buyerNick) {
        this.buyerNick = buyerNick == null ? null : buyerNick.trim();
    }

    /**
     * 获取评价时间
     *
     * @return comment_time - 评价时间
     */
    public Date getCommentTime() {
        return commentTime;
    }

    /**
     * 设置评价时间
     *
     * @param commentTime 评价时间
     */
    public void setCommentTime(Date commentTime) {
        this.commentTime = commentTime;
    }

    /**
     * 获取是否公开
     *
     * @return is_public - 是否公开
     */
    public Boolean getIsPublic() {
        return isPublic;
    }

    /**
     * 设置是否公开
     *
     * @param isPublic 是否公开
     */
    public void setIsPublic(Boolean isPublic) {
        this.isPublic = isPublic;
    }

    /**
     * 获取是否通过审核
     *
     * @return is_valid - 是否通过审核
     */
    public Boolean getIsValid() {
        return isValid;
    }

    /**
     * 设置是否通过审核
     *
     * @param isValid 是否通过审核
     */
    public void setIsValid(Boolean isValid) {
        this.isValid = isValid;
    }

    /**
     * 获取审核人id
     *
     * @return validation_user_id - 审核人id
     */
    public Long getValidationUserId() {
        return validationUserId;
    }

    /**
     * 设置审核人id
     *
     * @param validationUserId 审核人id
     */
    public void setValidationUserId(Long validationUserId) {
        this.validationUserId = validationUserId;
    }

    /**
     * 获取审核时间
     *
     * @return validation_time - 审核时间
     */
    public Date getValidationTime() {
        return validationTime;
    }

    /**
     * 设置审核时间
     *
     * @param validationTime 审核时间
     */
    public void setValidationTime(Date validationTime) {
        this.validationTime = validationTime;
    }

    /**
     * 获取审核意见
     *
     * @return validation_suggestion - 审核意见
     */
    public String getValidationSuggestion() {
        return validationSuggestion;
    }

    /**
     * 设置审核意见
     *
     * @param validationSuggestion 审核意见
     */
    public void setValidationSuggestion(String validationSuggestion) {
        this.validationSuggestion = validationSuggestion == null ? null : validationSuggestion.trim();
    }

    /**
     * 获取是否置顶
     *
     * @return is_top - 是否置顶
     */
    public Boolean getIsTop() {
        return isTop;
    }

    /**
     * 设置是否置顶
     *
     * @param isTop 是否置顶
     */
    public void setIsTop(Boolean isTop) {
        this.isTop = isTop;
    }

    /**
     * 获取评论用户id
     *
     * @return user_id - 评论用户id
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * 设置评论用户id
     *
     * @param userId 评论用户id
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