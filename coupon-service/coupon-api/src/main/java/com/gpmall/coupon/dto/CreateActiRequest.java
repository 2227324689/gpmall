package com.gpmall.coupon.dto;

import com.gpmall.commons.result.AbstractRequest;
import com.gpmall.commons.tool.exception.ValidateException;
import com.gpmall.coupon.SaleTypeEnum;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by oahnus on 2019/8/19
 * 23:37.
 */
@Data
public class CreateActiRequest extends AbstractRequest {
    private String name;
    private SaleTypeEnum saleType;
    private String desc;

    @Override
    public void requestCheck() {
        if (StringUtils.isEmpty(name)) {
            throw new ValidateException("缺少name参数");
        }
        if (saleType == null) {
            throw new ValidateException("缺少type参数");
        }
    }
}
