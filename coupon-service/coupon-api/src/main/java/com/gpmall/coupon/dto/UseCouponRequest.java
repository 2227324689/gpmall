package com.gpmall.coupon.dto;

import com.gpmall.commons.result.AbstractRequest;
import lombok.Data;

import java.util.List;

/**
 * Created by oahnus on 2019/8/19
 * 23:54.
 *
 * TODO 改名字。。。
 */
@Data
public class UseCouponRequest extends AbstractRequest {
    private List<OrderItemDto> orderItemList;

    @Override
    public void requestCheck() {

    }
}
