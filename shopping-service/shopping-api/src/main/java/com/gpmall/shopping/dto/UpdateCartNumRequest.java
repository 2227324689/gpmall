package com.gpmall.shopping.dto;

import com.gpmall.commons.result.AbstractRequest;
import com.gpmall.commons.tool.exception.ValidateException;
import com.gpmall.shopping.constants.ShoppingRetCode;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by mic on 2019/7/23.
 */
@Data
public class UpdateCartNumRequest extends AbstractRequest{
    private Long userId;
    private Long itemId;
    private Integer num;
    private String checked;

    @Override
    public void requestCheck() {
        if(userId==null||itemId==null||num==null|| StringUtils.isBlank(checked)){
            throw new ValidateException(ShoppingRetCode.REQUISITE_PARAMETER_NOT_EXIST.getCode(),ShoppingRetCode.REQUISITE_PARAMETER_NOT_EXIST.getMessage());
        }
    }
}
