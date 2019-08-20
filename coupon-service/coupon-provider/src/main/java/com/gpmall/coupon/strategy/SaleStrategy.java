package com.gpmall.coupon.strategy;

import com.gpmall.coupon.dto.UseCouponRequest;

/**
 * Created by oahnus on 2019/8/19
 * 23:54.
 */
public abstract class SaleStrategy {
    public abstract boolean match(UseCouponRequest request);
    public abstract void perform();
}
