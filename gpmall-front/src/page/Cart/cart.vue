<template>
  <div class="shopping-cart">
    <y-header>
      <div slot="nav"></div>
    </y-header>
    <div class="store-content page-cart">
      <div class="gray-box">
        <div class="title"><h2>购物清单</h2></div>
        <!--内容-->
        <div v-if="cartList.length">
          <div class="ui-cart">
            <div>
              <!--标题-->
              <div class="cart-table-title">
                <span class="name">商品信息</span> <span class="operation">操作</span> <span
                class="subtotal">小计</span> <span class="num">数量</span> <span class="price1">单价</span>
              </div>
              <!--列表-->
              <div class="cart-table" v-for="(item,i) in cartList" :key="i">
                <div class="cart-group divide pr" :data-productid="item.productId">
                  <div class="cart-top-items">
                    <div class="cart-items clearfix">
                      <!--勾选-->
                      <div class="items-choose">
                      <span class="blue-checkbox-new " :class="{'checkbox-on':item.checked === 'true'}"
                            @click="editCart('check',item)"></span>
                      </div>
                      <!--图片-->
                      <div class="items-thumb fl">
                        <img :alt="item.productName"
                             :src="item.productImg">
                        <a @click="goodsDetails(item.productId)" :title="item.productName" target="_blank"></a>
                      </div>
                      <!--信息-->
                      <div class="name hide-row fl">
                        <div class="name-table">
                          <a @click="goodsDetails(item.productId)" :title="item.productName" target="_blank"
                             v-text="item.productName"></a>
                          <!-- <ul class="attribute">
                            <li>白色</li>
                          </ul> -->
                        </div>
                      </div>
                      <!--删除按钮-->
                      <div class="operation">
                        <a class="items-delete-btn" @click="cartdel(item.productId)"></a>
                      </div>
                      <!--商品数量-->
                      <div>
                        <!--总价格-->
                        <div class="subtotal" style="font-size: 14px">¥ {{item.salePrice * item.productNum}}</div>
                        <!--数量-->
                        <buy-num :num="item.productNum"
                                 :id="item.productId"
                                 :checked="item.checked"
                                 style="height: 140px;
                                   display: flex;
                                   align-items: center;
                                   justify-content: center;"
                                 :limit="item.limitNum"
                                 @edit-num="EditNum"
                        >
                        </buy-num>
                        <!--价格-->
                        <div class="price1">¥ {{item.salePrice}}</div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <div class="cart-bottom-bg fix-bottom">
            <div class="fix-bottom-inner">
              <div class="cart-bar-operation">
                <div>
                  <div class="choose-all">
                    <span :class="{'checkbox-on':checkAllFlag}" class="blue-checkbox-new" @click="editCheckAll"></span>
                    <span @click="editCheckAll">全选</span>
                  </div>
                  <div class="delete-choose-goods" @click="delChecked">删除选中的商品</div>
                </div>
              </div>
              <div class="shipping">
                <div class="shipping-box">
                  <div class="shipping-total shipping-num"><h4
                    class="highlight">已选择 <i v-text="checkNum"></i> 件商品</h4>
                    <h5>共计 <i v-text="totalNum"></i> 件商品</h5></div>
                  <div class="shipping-total shipping-price"><h4
                    class="highlight">应付总额：<span>￥</span><i v-text="checkPrice"></i>
                  </h4>
                    <h5 class="shipping-tips ng-scope">应付总额不含运费</h5>
                  </div>
                </div>
                <y-button :classStyle="checkNum > 0 && submit?'main-btn':'disabled-btn'"
                          class="big-main-btn"
                          style="margin: 0;width: 130px;height: 50px;line-height: 50px;font-size: 16px"
                          :text="checkoutNow" @btnClick="checkout"></y-button>
              </div>
            </div>
          </div>
        </div>
        <div v-else style="padding:50px">
          <div class="cart-e">
          </div>
          <p style="text-align: center;padding: 20px;color: #8d8d8d">你的购物车空空如也</p>
          <div style="text-align: center">
            <router-link to="/goods">
              <y-button text="现在选购" style="width: 150px;height: 40px;line-height: 38px;color: #8d8d8d"></y-button>
            </router-link>
          </div>

        </div>
      </div>
    </div>
    <y-footer></y-footer>
  </div>
</template>
<script>
  import { getCartList, cartEdit, editCheckAll, cartDel, delCartChecked } from '/api/goods'
  import { mapMutations, mapState } from 'vuex'
  import YButton from '/components/YButton'
  import YHeader from '/common/header'
  import YFooter from '/common/footer'
  import BuyNum from '/components/buynum'
  import { getStore } from '/utils/storage'
  export default {
    data () {
      return {
        userId: 0,
        checkoutNow: '现在结算',
        submit: true
      }
    },
    computed: {
      ...mapState(
        ['cartList']
      ),
      // 是否全选
      checkAllFlag () {
        return this.checkedCount === this.cartList.length
      },
      // 勾选的数量
      checkedCount () {
        var i = 0
        this.cartList && this.cartList.forEach((item) => {
          if (item.checked === 'true') i++
        })
        return Number(i)
      },
      // 计算总数量
      totalNum () {
        var totalNum = 0
        this.cartList && this.cartList.forEach(item => {
          totalNum += (item.productNum)
        })
        return Number(totalNum)
      },
      // 选中的总价格
      checkPrice () {
        var totalPrice = 0
        this.cartList && this.cartList.forEach(item => {
          if (item.checked === 'true') {
            totalPrice += (item.productNum * item.salePrice)
          }
        })
        return totalPrice
      },
      // 选中的商品数量
      checkNum () {
        var checkNum = 0
        this.cartList && this.cartList.forEach(item => {
          if (item.checked === 'true') {
            checkNum += (item.productNum)
          }
        })
        return checkNum
      }
    },
    methods: {
      ...mapMutations([
        'INIT_BUYCART', 'EDIT_CART'
      ]),
      message (m) {
        this.$message.error({
          message: m
        })
      },
      goodsDetails (id) {
        window.open(window.location.origin + '#/product/' + id)
      },
      // 全选
      editCheckAll () {
        let checkAll = !this.checkAllFlag
        editCheckAll({userId: this.userId, checked: checkAll}).then(res => {
          this.EDIT_CART({checked: checkAll})
        })
      },
      // 修改购物车
      _cartEdit (userId, productId, productNum, checked) {
        cartEdit(
          {
            userId,
            productId,
            productNum,
            checked
          }
        ).then(res => {
          if (res.success === true) {
            this.EDIT_CART(
              {
                productId,
                checked,
                productNum
              }
            )
          }
        })
      },
      // 修改购物车
      editCart (type, item) {
        if (type && item) {
          let checked = item.checked
          let productId = item.productId
          let productNum = item.productNum
          // 勾选
          if (type === 'check') {
            let newChecked = checked === 'true' ? 'false' : 'true'
            this._cartEdit(this.userId, productId, productNum, newChecked)
          }
        } else {
          console.log('缺少所需参数')
        }
      },
      EditNum (productNum, productId, checked) { // 数量
        this._cartEdit(this.userId, productId, productNum, checked)
      },
      // 删除整条购物车
      cartdel (productId) {
        cartDel({userId: this.userId, productId: productId}).then(res => {
          this.EDIT_CART({productId})
        })
      },
      checkout () {
        this.checkoutNow = '结算中...'
        this.submit = false
        this.$router.push({path: 'checkout'})
      },
      delChecked () {
        getCartList().then(res => {
          if (res.success === true) {
            res.result.forEach(item => {
              if (item.checked === 'true') {
                let productId = item.productId
                this.EDIT_CART({productId})
              }
            })
          }
        })
        delCartChecked({userId: this.userId}).then(res => {
          if (res.success !== true) {
            this.message('删除失败')
          }
        })
      }
    },
    mounted () {
      this.userId = getStore('userId')
      this.INIT_BUYCART()
    },
    components: {
      YButton,
      YHeader,
      YFooter,
      BuyNum
    }
  }
</script>
<style lang="scss" rel="stylesheet/scss" scoped>
  .store-content {
    clear: both;
    width: 1220px;
    min-height: 600px;
    padding: 0 0 25px;
    margin: 0 auto;
    .gray-box {
      position: relative;
      margin-bottom: 30px;
      overflow: hidden;
      background: #fff;
      border-radius: 8px;
      border: 1px solid #dcdcdc;
      border-color: rgba(0, 0, 0, .14);
      box-shadow: 0 3px 8px -6px rgba(0, 0, 0, .1);
      .title {
        padding-left: 30px;
        position: relative;
        z-index: 10;
        height: 60px;
        padding: 0 10px 0 24px;
        border-bottom: 1px solid #d4d4d4;
        border-radius: 8px 8px 0 0;
        box-shadow: rgba(0, 0, 0, .06) 0 1px 7px;
        background: #f3f3f3;
        background: -webkit-linear-gradient(#fbfbfb, #ececec);
        background: linear-gradient(#fbfbfb, #ececec);
        line-height: 60px;
        font-size: 18px;
        color: #333;
      }
    }
    .ui-cart {
      padding-bottom: 91px;
      .cart-table-title {
        position: relative;
        z-index: 1;
        line-height: 38px;
        height: 38px;
        padding: 0 0 0 30px;
        font-size: 12px;
        background: #eee;
        border-bottom: 1px solid #dbdbdb;
        border-bottom-color: rgba(0, 0, 0, .08);
        .name {
          float: left;
          text-align: left;
        }
        span {
          width: 137px;
          float: right;
          text-align: center;
          color: #838383;
        }
      }
      .cart-group.divide {
        .cart-items {
          border-top: 1px dashed #eee;
        }
      }
      .cart-items {
        position: relative;
        height: 140px;
        margin-left: 74px;
        /*删除*/
        .operation {
          padding: 58px 0 0;
          font-size: 12px;
          line-height: 24px;
          .items-delete-btn {
            background-image: url(../../../static/images/delete-btn-icon_a35bf2437e@2x.jpg);
            &:hover {
              background-position: 0 -36px;
            }
          }
          .items-delete-btn {
            display: block;
            width: 24px;
            height: 24px;
            margin: 0 auto;
            color: #C2C2C2;
            background: url(../../../static/images/delete-btn-icon_a35bf2437e@2x.jpg);
            -webkit-background-size: 100% auto;
            background-size: 100% auto;
            -moz-transition: none;
            -webkit-transition: none;
            -o-transition: none;
            transition: none;
          }
        }
        .subtotal {
          font-weight: 700;
        }
        .item-cols-num,
        .operation,
        .price1,
        .subtotal {
          overflow: hidden;
          float: right;
          width: 137px;
          text-align: center;
          color: #666;
          line-height: 140px;
        }
      }
      .cart-group.divide .cart-top-items:first-child .cart-items {
        border-top: none;
      }
      .items-choose {
        position: absolute;
        left: -74px;
        top: 0;
        width: 74px;
        height: 20px;
        padding: 60px 0 0 31px;
        font-size: 12px;
        color: #999;
      }
      .items-thumb {
        position: relative;
        margin-top: 30px;
        overflow: hidden;
        width: 80px;
        height: 80px;
      }
      img {
        width: 80px;
        height: 80px;
      }
      .cart-items .items-thumb > a, .ui-cart .cart-items .items-thumb > i {
        position: absolute;
        left: 0;
        right: 0;
        top: 0;
        bottom: 0;
        border: 1px solid #fff;
        border-radius: 3px;
        border: 0 solid rgba(255, 255, 255, .1);
        box-shadow: inset 0 0 0 1px rgba(0, 0, 0, .06);
      }
      .name {
        width: 380px;
        margin-left: 20px;
        color: #323232;
        display: table;
        a {
          color: #333;
          font-size: 16px;
        }
      }
      .name-table {
        display: table-cell;
        vertical-align: middle;
        height: 140px;
      }
      .attribute, .name p {
        color: #999;
        font-size: 12px;
        padding-top: 4px;
        line-height: 17px;
      }

    }

  }

  .page-cart {
    padding-top: 40px;
    .fix-bottom {
      height: 90px;
      width: 100%;
      position: absolute;
      bottom: 0;
      z-index: 1;
      background-position: center;
      background: #fdfdfd;
      background: -webkit-linear-gradient(#fdfdfd, #f9f9f9);
      background: linear-gradient(#fdfdfd, #f9f9f9);
      border-top: 1px solid #e9e9e9;
      box-shadow: 0 -3px 8px rgba(0, 0, 0, .04);
      .cart-bottom-bg {
        height: 80px;
        /*background: url(../img/store/library/cart-wrapper-bg_4c8003c76e.jpg) repeat-x;*/
        border-top: 1px solid #D9D9D9;
        border-radius: 0 0 8px 8px;
      }
    }
    .cart-bar-operation {
      float: left;
      padding: 35px 26px;
      font-size: 12px;
    }
    .blue-checkbox-new {
      float: left;
      margin-right: 9px;
    }
    .choose-all, .delete-choose-goods, .selected-count {
      float: left;
      height: 20px;
      line-height: 20px;
      cursor: pointer;
      position: relative;
    }
    .blue-checkbox-new, .blue-checkbox-new.checkbox-disable, .blue-checkbox-new.checkbox-on {
      display: inline-block;
      position: relative;
      width: 20px;
      height: 20px;
      background: url(../../../static/images/checkbox-new_631a56a4f6.png) no-repeat 0 -20px;
      cursor: pointer;
      -moz-user-select: none;
      -webkit-user-select: none;
      -ms-user-select: none;
      user-select: none;
      vertical-align: middle;
    }

    .blue-checkbox-new.checkbox-on, .choose-checkbox-on .blue-checkbox-new {
      background: url(../../../static/images/checkbox-new_631a56a4f6.png) no-repeat 0 0;
    }
    .delete-choose-goods {
      position: relative;
      margin-left: 21px;
      color: #bbb;
    }
    .shipping {
      float: right;
      padding: 20px 30px;
    }
    .shipping-box {
      display: inline-block;
      padding-top: 1px;
      margin-right: 10px;
    }
    .shipping-total {
      display: inline-block;
      border-left: 1px solid #e1e1e1;
      padding: 0 20px;
      .shipping-price {
        width: 155px;
        padding-right: 0;
      }
      &.shipping-num i {
        width: 28px;
        display: inline-block;
        text-align: center;
      }
      h4 {
        color: #323232;
        > i {
          font-size: 18px;
          font-weight: 700;
        }
        i, span {
          color: #d44d44;
        }

      }
      h5 {
        color: #959595;
        > i {
          font-size: 16px;
          font-weight: 700;
        }
      }

    }

    .shipping-total.shipping-num {
      text-align: right;
    }
    .shipping-total:first-child {
      border: none;
    }
    .big-main-btn {
      float: right;
      height: 48px;
    }
  }

  .cart-e {
    margin: 0 auto;
    background: url("/static/images/cart-empty_@2x.png") no-repeat;
    width: 275px;
    height: 300px;
    color: #8d8d8d;
  }


</style>
