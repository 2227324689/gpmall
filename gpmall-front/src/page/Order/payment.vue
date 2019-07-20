<template>
  <div class="w" style="padding-bottom: 100px;">
    <y-shelf title="支付订单">
      <div slot="content">
        <div class="box-inner order-info">
          <h3>提交订单成功，请填写捐赠信息</h3>
          <p class="payment-detail">请在 <span>24 小时内</span>完成支付，超时订单将自动取消。</p>
          <p class="payment-detail">我们不会在您完成支付后的 72 小时内发货，您的支付将用作捐赠</p>
          <p class="payment-detail" style="color:red">请仔细填写捐赠信息，避免系统审核失败无法在捐赠名单中显示您的数据</p>
        </div>
        <div class="pay-info">
          <span style="color:red">*</span> 昵称：<el-input v-model="nickName" placeholder="请输入您的昵称" @change="checkValid" :maxlength="maxLength" class="input"></el-input><br>
          <span style="color:red">*</span> 捐赠金额：<el-select class="money-select" v-model="moneySelect" placeholder="请选择支付金额" @change="changeSelect">
            <el-option label="￥0.10 我是穷逼" value="0.10"></el-option>
            <el-option label="￥1.00 支付测试" value="1.00"></el-option>
            <el-option label="￥5.00 感谢捐赠" value="5.00"></el-option>
            <el-option label="￥10.00 感谢大佬" value="10.00"></el-option>
            <el-option label="自定义 随意撒币" value="custom"></el-option>
          </el-select><br>
          <div v-if="moneySelect === 'custom'"><span style="color:red">*</span> 输入金额：<el-input v-model="money" placeholder="请输入捐赠金额(最多2位小数，不得低于0.1元)" @change="checkValid" :maxlength="maxLength" class="input" style="margin-left:10px"></el-input><br></div>
          <span style="color:red">*</span> 通知邮箱：<el-input v-model="email" placeholder="支付审核结果将以邮件方式发送至您的邮箱" @change="checkValid" :maxlength="maxLength" class="input" style="margin-left:10px"></el-input><br>
          &nbsp;&nbsp; 留言：<el-input v-model="info" placeholder="请输入您的留言内容" :maxlength="maxLength" class="input"></el-input>
        </div>
        <!--支付方式-->
        <div class="pay-type">
          <div class="p-title">支付方式</div>
          <div class="pay-item">
            <div :class="{active:payType==1}" @click="payType=1"><img src="/static/images/alipay@2x.png" alt=""></div>
            <div :class="{active:payType==2}" @click="payType=2"><img src="/static/images/weixinpay@2x.png" alt=""></div>
            <div :class="{active:payType==3}" @click="payType=3"><img src="/static/images/qqpay.png" alt=""></div>
          </div>
        </div>

        <div>
          <div class="box-inner">
            <div>
              <span>
                订单金额：
              </span>
              <em><span>¥</span>{{orderTotal.toFixed(2)}}</em>
              <span>
                实际应付金额：
              </span>
              <em><span>¥</span>{{money}}</em>
              <y-button :text="payNow"
                        :classStyle="submit?'main-btn':'disabled-btn'"
                        style="width: 120px;height: 40px;font-size: 16px;line-height: 38px"
                        @btnClick="paySuc()"
              ></y-button>
            </div>
          </div>
        </div>

      </div>
    </y-shelf>
    <!--地址信息-->
    <div class="p-msg w">
      <div class="confirm-detail">
        <div class="info-title">收货信息</div>
        <p class="info-detail">姓名：{{userName}}</p>
        <p class="info-detail">联系电话：{{tel}}</p>
        <p class="info-detail">详细地址：{{streetName}}</p></div>
    </div>
    <div class="confirm-table-title">
      <span class="name">商品信息</span>
      <div>
        <span class="price">单价</span>
        <span class="num">数量</span>
        <span class="subtotal">小计</span>
      </div>
    </div>
    <!--商品-->
    <div class="confirm-goods-table">
      <div class="cart-items" v-for="(item,i) in cartList" :key="i">
        <div class="name">
          <div class="name-cell ellipsis">
            <a @click="goodsDetails(item.productId)" title="" target="_blank">{{item.productName}}</a>
          </div>
        </div>
        <div class="n-b">
          <div class="price">¥ {{item.salePrice}}</div>
          <div class="goods-num">{{item.productNum}}</div>
          <div class="subtotal">
            <div class="subtotal-cell"> ¥ {{item.salePrice * item.productNum}}<br></div>
          </div>
        </div>
      </div>
    </div>
    <!--合计-->
    <div class="order-discount-line">
      <p style="font-size: 14px;font-weight: bolder;"> <span style="padding-right:47px">商品总计：</span>
        <span style="font-size: 16px;font-weight: 500;line-height: 32px;">¥ {{orderTotal}}</span>
      </p>
      <p><span style="padding-right:30px">运费：</span><span style="font-weight: 700;">+ ¥ 0.00</span></p>
    </div>
  </div>
</template>
<script>
  import YShelf from '/components/shelf'
  import YButton from '/components/YButton'
  import { getOrderDet, payMent } from '/api/goods'
  import { getStore, setStore } from '/utils/storage'
  export default {
    data () {
      return {
        payType: 1,
        addList: {},
        cartList: [],
        addressId: 0,
        productId: '',
        num: '',
        userId: '',
        orderTotal: 0,
        userName: '',
        tel: '',
        streetName: '',
        payNow: '立刻支付',
        submit: false,
        nickName: '',
        money: '1.00',
        info: '',
        email: '',
        orderId: '',
        type: '',
        moneySelect: '1.00',
        isCustom: false,
        maxLength: 30
      }
    },
    computed: {
      // 选中的总价格
      checkPrice () {
        let totalPrice = 0
        this.cartList && this.cartList.forEach(item => {
          if (item.checked === '1') {
            totalPrice += (item.productNum * item.salePrice)
          }
        })
        return totalPrice
      }
    },
    methods: {
      checkValid () {
        if (this.nickName !== '' && this.money !== '' && this.isMoney(this.money) && this.email !== '' && this.isEmail(this.email)) {
          this.submit = true
        } else {
          this.submit = false
        }
      },
      messageFail (m) {
        this.$message.error({
          message: m
        })
      },
      changeSelect (v) {
        if (v !== 'custom') {
          this.money = v
        } else {
          this.isCustom = true
          this.money = ''
        }
        this.checkValid()
      },
      goodsDetails (id) {
        window.open(window.location.origin + '#/goodsDetails?productId=' + id)
      },
      _getOrderDet (orderId) {
        let params = {
          params: {
            orderId: this.orderId
          }
        }
        getOrderDet(params).then(res => {
          this.cartList = res.result.goodsList
          this.userName = res.result.addressInfo.userName
          this.tel = res.result.addressInfo.tel
          this.streetName = res.result.addressInfo.streetName
          this.orderTotal = res.result.orderTotal
        })
      },
      paySuc () {
        this.payNow = '支付中...'
        this.submit = false
        if (this.payType === 1) {
          this.type = 'Alipay'
        } else if (this.payType === 2) {
          this.type = 'Wechat'
        } else if (this.payType === 3) {
          this.type = 'QQ'
        } else {
          this.type = '其它'
        }
        payMent({
          nickName: this.nickName,
          money: this.money,
          info: this.info,
          email: this.email,
          orderId: this.orderId,
          userId: this.userId,
          payType: this.type
        }).then(res => {
          if (res.success === true) {
            setStore('setTime', 90)
            setStore('price', this.money)
            setStore('isCustom', this.isCustom)
            if (this.payType === 1) {
              this.$router.push({path: '/order/alipay'})
            } else if (this.payType === 2) {
              this.$router.push({path: '/order/wechat'})
            } else if (this.payType === 3) {
              this.$router.push({path: '/order/qqpay'})
            } else {
              this.$router.push({path: '/order/alipay'})
            }
          } else {
            this.payNow = '立刻支付'
            this.submit = true
            this.messageFail(res.message)
          }
        })
      },
      isMoney (v) {
        if (v < 0.1) {
          return false
        }
        var regu = /(^[1-9]([0-9]+)?(\.[0-9]{1,2})?$)|(^(0){1}$)|(^[0-9]\.[0-9]([0-9])?$)/
        var re = new RegExp(regu)
        if (re.test(v)) {
          return true
        } else {
          return false
        }
      },
      isEmail (v) {
        var regu = /^[a-z0-9]+([._\\-]*[a-z0-9])*@([a-z0-9]+[-a-z0-9]*[a-z0-9]+.){1,63}[a-z0-9]+$/
        var re = new RegExp(regu)
        if (re.test(v)) {
          return true
        } else {
          return false
        }
      }
    },
    created () {
      this.userId = getStore('userId')
      this.orderId = this.$route.query.orderId
      if (this.orderId) {
        this._getOrderDet(this.orderId)
      } else {
        this.$router.push({path: '/'})
      }
    },
    components: {
      YShelf,
      YButton
    }
  }
</script>
<style lang="scss" scoped rel="stylesheet/scss">
  .w {
    padding-top: 39px;
  }

  .order-info {
    padding: 60px 0 55px;
    color: #333;
    background: #fff !important;
    h3 {
      padding-bottom: 14px;
      line-height: 36px;
      text-align: center;
      font-size: 36px;
      color: #212121;
    }
    .payment-detail {
      text-align: center;
      line-height: 24px;
      font-size: 14px;
      color: #999;
    }
  }

  /*支付类型*/
  .pay-type {
    margin: 0 auto;
    width: 90%;
    padding-bottom: 30px;
    .p-title {
      font-size: 18px;
      line-height: 40px;
      padding: 0 10px;
      position: relative;
      &:before {
        content: ' ';
        position: absolute;
        bottom: 0;
        left: 0;
        right: 0;
        border-bottom: 1px solid #ccc;
      }
    }

  }

  .pay-type {
    .pay-item {
      display: flex;
      align-items: center;
      div {
        margin-top: 20px;
        width: 175px;
        height: 50px;
        display: flex;
        align-items: center;
        justify-content: center;
        border: 1px solid #E5E5E5;
        cursor: pointer;
        border-radius: 6px;
        margin-right: 10px;
        background: #FAFAFA;
        &.active {
          border-color: #6A8FE5;
          background: #fff;
        }
        img {
          display: block;
          height: 34px;
          margin: 8px auto;
        }
      }
    }
  }

  .box-inner {
    line-height: 60px;
    background: #f9f9f9;
    border-top: 1px solid #e5e5e5;
    box-sizing: border-box;
    > div {
      display: flex;
      justify-content: flex-end;
      align-items: center;
      padding: 0 20px;
    }
    em {
      margin-left: 5px;
      font-size: 24px;
      color: #d44d44;
      font-weight: 700;
      margin-right: 20px;
      span {
        margin-right: 4px;
        font-size: 16px;

      }
    }
  }

  .confirm-detail {
    padding: 0 30px 25px;
    border-top: 1px solid #d5d5d5;
    .info-title {
      height: 14px;
      margin: 30px 0 17px;
      line-height: 14px;
      font-weight: bolder;
      color: #333;
    }
    .info-detail {
      line-height: 24px;
      color: #666;
    }
  }

  .confirm-table-title {
    padding: 3px 0 0 33px;
    border-top: 1px solid #D5D5D5;
    line-height: 54px;
    font-weight: bolder;
    color: #000;
    display: flex;
    justify-content: space-between;
    span {
      display: inline-block;
      width: 175px;
      text-align: left;
    }
    .price {
      padding-left: 80px;
    }
    .num {
      padding-left: 75px;
    }
    .subtotal {
      padding-left: 72px;
    }
  }

  .confirm-goods-table {
    border-top: 1px solid #D5D5D5;
    .cart-items {
      height: 80px;
      display: flex;
      align-items: center;
      justify-content: space-between;
      a {
        color: #333;
      }
    }
    .n-b {
      display: flex;
      align-items: center;
      justify-content: center;
      > div {
        color: #626262;
        font-weight: 700;
        width: 175px;
        text-align: center;
      }
    }
  }

  .order-discount-line {
    padding: 22px 30px 53px;
    border-top: 1px solid #D5D5D5;
    line-height: 24px;
    text-align: right;
    font-size: 12px;
    &:first-child {
      line-height: 32px;
      text-align: right;
      font-size: 14px;
      font-weight: bolder;
    }
  }

  .name {
    width: 40%;
  }

  .name-cell {
    padding-left: 33px;
  }

  .input {
    width:30%;
    margin:0 0 1vw 38px;
  }

  .pay-info {
    margin-top: -2vw;
    text-align: center;
  }

  .money-select {
    width: 31%;
    padding-left: 10px;
    margin-bottom: 1vw;
  }
</style>
