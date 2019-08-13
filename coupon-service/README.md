# 优惠券设计

领券中心
优惠券的限制

暂未考虑活动
```
优惠券
tb_coupon

id
name 
desc
type  // 1 满减券 2 通用减价券
discount_price // 减金额
full_price  // 满金额
is_visible // 是否可用
count // 数量
grab_start_time  // 抢券开始时间  发券中心备用
grab_end_time  
start_time  // 优惠券有效期
end_time
created
updated

优惠码
---------
tb_coupon_code

id
code  // 优惠码 订单使用优惠码  // 按码领券
coupon_id
status 0 未领取 1 已领取未使用 2 冻结中 3 已使用 4 已过期
user_id 
order_id  // 消费的订单明细 id
consume_time  // 使用时间
created  
updated

```
