package com.gpmall.coupon;

import java.util.List;

/**
 * Created by oahnus on 2019/8/10
 * 13:16.
 */
public interface ICouponService {
    void create(); // 创建
    void delete(); // 删除
    void update(); // 修改
    void dispatchCoupon(Integer couponInfoId, List<Integer> userIdList);

    void freeze(Integer couponCodeId); // 冻结
    void consume(Integer couponCodeId);  // 使用
}
