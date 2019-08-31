<template>
  <div class="w" style="padding-bottom: 100px;">
    <y-shelf title="支付订单">
      <div slot="content">
        <div class="box-inner order-info">
          <h3>提交订单成功，请选择支付方式</h3>
          <p class="payment-detail">请在 <span>2 小时内</span>完成支付，超时订单将自动取消。</p>
          <p class="payment-detail">我们不会在您完成支付后的 72 小时内发货，您的支付将用作捐赠</p>
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
              <em><span>¥</span>{{orderTotal.toFixed(2)}}</em>
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
  </div>
</template>
<script>
  import YShelf from '/components/shelf'
  import YButton from '/components/YButton'
  import { getOrderDet, payMent } from '/api/goods'
  export default {
    data () {
      return {
        payType: 1,
        addList: {},
        cartList: [],
        addressId: 0,
        productId: '',
        num: '',
        orderTotal: 0,
        userName: '',
        tel: '',
        streetName: '',
        payNow: '立刻支付',
        submit: true,
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
          if (item.checked) {
            totalPrice += (item.productNum * item.salePrice)
          }
        })
        return totalPrice
      }
    },
    methods: {
      open (t, m) {
        this.$notify.info({
          title: t,
          message: m
        })
      },
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
        window.open(window.location.origin + '#/product/' + id)
      },
      _getOrderDet (orderId) {
        let params = {
          params: {
            orderId: this.orderId
          }
        }
        getOrderDet(params).then(res => {
          this.cartList = res.result.goodsList
          this.userName = res.result.userName
          this.orderTotal = res.result.orderTotal
        })
      },
      paySuc () {
        this.payNow = '支付中...'
        this.submit = false
        if (this.payType === 1) {
          this.type = 'alipay'
        } else if (this.payType === 2) {
          this.type = 'wechat_pay'
        } else if (this.payType === 3) {
          this.type = 'QQ'
          this.open('提示', '暂不支持qq钱包支付')
          return
        } else {
          this.type = '其它'
        }
        const info = this.cartList[0].title
        payMent({
          nickName: this.userName,
          money: this.money,
          info: info,
          orderId: this.orderId,
          payType: this.type
        }).then(res => {
          if (res.success === true) {
            if (this.payType === 1) {
              const div = document.createElement('div')
              div.innerHTML = res.result
              document.body.appendChild(div)
              document.forms.alipaysubmit.submit()
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
      this.orderId = this.$route.params.orderId
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
