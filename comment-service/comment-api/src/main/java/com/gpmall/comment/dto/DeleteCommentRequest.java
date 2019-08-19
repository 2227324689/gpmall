package com.gpmall.comment.dto;

import com.gpmall.comment.constant.CommentRetCode;
import com.gpmall.commons.result.AbstractRequest;
import com.gpmall.commons.tool.exception.ValidateException;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

/**
 * @author heps
 * @date 2019/8/18 8:29
 * 删除某个商品评价请求参数
 */
@Data
public class DeleteCommentRequest extends AbstractRequest {

    private String commentId;

    private Long userId;

    @Override
    public void requestCheck() {
        if (StringUtils.isEmpty(commentId) || userId == null) {
            throw new ValidateException(CommentRetCode.REQUISITE_PARAMETER_NOT_EXIST.getCode(),CommentRetCode.REQUISITE_PARAMETER_NOT_EXIST.getMessage());
        }
    }
}
