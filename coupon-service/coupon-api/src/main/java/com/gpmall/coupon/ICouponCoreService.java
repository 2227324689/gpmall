package com.gpmall.coupon;

/**
 * Created by oahnus on 2019/8/16
 * 0:09.
 */
public interface ICouponCoreService {
    // admin创建优惠券
    // admin修改优惠券
    // admin删除优惠券

    // 订单支付过程中 冻结优惠券
    void freezeCoupon();
    // 订单取消后, 解冻优惠券
    void unfreezeCoupon();

    // 根据优惠券id生成优惠码
    void generateCouponCode(Integer couponId);

    // 派发优惠券 couponId userId
    void dispatchCoupon(Integer couponId, Integer... userId);
    // 优惠码兑换优惠券
    void exchangeCoupon();
}
