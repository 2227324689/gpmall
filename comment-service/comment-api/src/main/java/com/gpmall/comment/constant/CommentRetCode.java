package com.gpmall.comment.constant;

/**
 * @author heps
 * @date 2019/8/12 20:30
 * comment-service统一错误码为 006
 */
public enum CommentRetCode {
    SUCCESS("000000", "成功"),
    REQUISITE_PARAMETER_NOT_EXIST("006001", "必要的参数不能为空"),
    EXIST_SENSITIVE_WORDS("006002", "评论中存在敏感词"),
    CURRENT_ORDER_ITEM_EXISTS_COMMENT("006003", "当前商品已经评价, 不能重复添加"),
    COMMENT_NOT_EXIST("006004", "当前商品没有评价"),
    CURRENT_COMMENT_NOT_EXIST("006005", "该评价不存在"),
    REQUEST_PARAMETER_ERROR("006006", "请求参数错误"),
    ORIGIN_COMMENT_NOT_EXIST("006007", "原评价不存在"),
    CURRENT_COMMENT_REPLY_NOT_EXIST("006008", "该回复意见不存在"),
    SYSTEM_ERROR("006500", "系统错误");

    /**
     * 错误码
     */
    private String code;

    /**
     * 错误提示信息
     */
    private String message;

    CommentRetCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String getMessage(String code) {
        for (CommentRetCode value : values()) {
            if (value.code.equals(code)) {
                return value.message;
            }
        }
        return null;
    }
}
