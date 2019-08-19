package com.gpmall.comment.dto;

import com.gpmall.comment.constant.CommentRetCode;
import com.gpmall.commons.result.AbstractRequest;
import com.gpmall.commons.tool.exception.ValidateException;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

/**
 * @author heps
 * @date 2019/8/15 23:01
 * 分页查询单个商品的商品评价参数
 */
@Data
public class CommentListRequest extends AbstractRequest {

    private String itemId;

    /**
     * 评价类型 1、好评 2、中评 3、差评
     */
    private Integer type;

    private int page;

    private int size;

    @Override
    public void requestCheck() {
        if (StringUtils.isEmpty(itemId)) {
            throw new ValidateException(CommentRetCode.REQUISITE_PARAMETER_NOT_EXIST.getCode(),CommentRetCode.REQUISITE_PARAMETER_NOT_EXIST.getMessage());
        }
        if (type != null && (type < 1 || type > 3)) {
            type = null;
        }
        if (page < 1) {
            setPage(1);
        }
        if (size < 1) {
            size = 10;
        }
    }
}
