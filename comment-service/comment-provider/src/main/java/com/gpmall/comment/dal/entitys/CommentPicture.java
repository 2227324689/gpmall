package com.gpmall.comment.dal.entitys;

import lombok.Data;

import javax.persistence.*;

@Table(name = "tb_comment_picture")
@Data
public class CommentPicture {
    /**
     * 商品评价图片id
     */
    @Id
    private String id;

    /**
     * 商品评价id
     */
    @Column(name = "comment_id")
    private String commentId;

    /**
     * 图片路径
     */
    @Column(name = "pic_path")
    private String picPath;

    /**
     * 获取商品评价图片id
     *
     * @return id - 商品评价图片id
     */
    public String getId() {
        return id;
    }

    /**
     * 设置商品评价图片id
     *
     * @param id 商品评价图片id
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
     * 获取图片路径
     *
     * @return pic_path - 图片路径
     */
    public String getPicPath() {
        return picPath;
    }

    /**
     * 设置图片路径
     *
     * @param picPath 图片路径
     */
    public void setPicPath(String picPath) {
        this.picPath = picPath == null ? null : picPath.trim();
    }
}