# 营销服务

[TOC]

==优惠券coupon-api模块目前仍未草稿， 不建议install到本地包使用==

## 场景

### 活动

1. 用户在活动期间购买参与活动的商品，可以享受优惠价格（满减，满折等）
2. 用于可以通过浏览活动页，领取优惠券

### 优惠券

领取：

1. 给新注册的用户发送券

   通过系统自动绑定优惠券

2. 消费满额送优惠券

   用户订单金额消费满一定数值后对用户发送优惠券

3. 管理后台绑定优惠券

4. 活动页点击送券

5. 批量生成兑换码，凭码领券

使用：

用户下单时，可以选择使用一张或多张优惠券。

已使用的优惠券在订单成功支付或取消订单之前，不可再次使用



## 需求

### 活动

1. 允许管理人员创建，修改，删除营销活动，活动可能涉及单一的具体商品，也可能是涉及全部商品的全场活动

2. 允许查询正在进行中的活动列表以及所有活动列表

3. 活动要有起止时间，活动类型包括（满减，满赠，满折，秒杀等类型）

   - 满减  订单金额满足指定数值时，减金额

   - 满赠  订单金额满足指定数值时，赠产品
   - 满折  订单金额满足指定数值时，按总金额打折计算



### 优惠券

1. 允许管理人员创建，修改，删除优惠券，
2. 购物车结算时，获取用户可用的优惠券列表
3. 创建订单后，检查优惠券是否满足使用条件，满足条件后要锁定已勾选的优惠券, 将优惠券设置为冻结状态，在订单支付完成或取消订单后，再次修改优惠券状态



OrderService传递活动id, 优惠码code给SaleService，SaleService冻结优惠券，计算优惠金额，返回优惠总金额，赠送产品id,营销类型等数据给OrderService，由OrderService处理后续业务



## 时序图草稿

processon

https://www.processon.com/view/link/5c9f4432e4b00f8893eef137

![](http://assets.processon.com/chart_image/5c9f4431e4b029f6dae0bf8a.png?_=1565597776676)

### 领取优惠券详细时序





### 使用优惠券详细时序

https://www.processon.com/view/link/5c9f418fe4b029f6dae0be0a



![](http://assets.processon.com/chart_image/5c9f418ee4b06765f07f2695.png?_=1565886383720)







## 数据库设计



```
TABLE_NAME: tb_sale_acti  // 营销活动表

id
name,
type, // 类型
status, // 状态
start_time, // 开始结束时间
end_time, 
desc 
created
updated
```


```
TABLE_NAME: tb_sale_rule // 活动规则关联表

id,
acti_id, // 活动id
item_id, // 满足规则的产品id  
full_price  // 满金额
discount_price // 抵扣金额
discount_rate // 折扣 比例
gift_item_id  // 赠送产品id
overlap  // 是否可叠加
desc, // 描述
created
updated
```



```
TABLE_name: tb_sale_coupon  // 优惠券

id
name 
desc
type // 优惠券类型
rule_id // 关联的规则id
is_visible // 是否可用
count // 数量
grab_start_time  // 抢券开始时间  领券中心备用
grab_end_time  
start_time  // 优惠券有效期
end_time,
created
updated
```

```
TABLE_NAME: tb_sale_coupon_code  // 优惠码

id
code  // 优惠码 按码领券
coupon_id  // 关联优惠码
status 0 未领取 1 已领取未使用 2 冻结中 3 已使用 4 已过期
user_id  // 关联用户
order_id  // 消费的订单明细 id
consume_time  // 使用时间
created
updated
```

```
TABLE_NAME: tb_queue_msg  // 消息队列消息表 （异常消息补偿）

id
queue // 队列名称
topic  // 队列主题
method // 执行的方法
args // 方法参数
finish_time // handle执行完成时间
created
```
