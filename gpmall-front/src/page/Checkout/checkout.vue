<template>
  <div class="checkout">
    <y-header>
      <div slot="nav"></div>
    </y-header>
    <div class="w" style="padding-top: 40px;">
      <!-- 收货地址 -->
      <y-shelf title="收货信息">
        <div slot="content">
          <ul class="address-item-list clearfix">
            <li v-for="(item,i) in addList"
                :key="i"
                class="address pr"
                :class="{checked:addressId === item.addressId}"
                @click="chooseAddress(item.addressId, item.userName, item.tel, item.streetName)">
           <span v-if="addressId === item.addressId" class="pa">
             <svg viewBox="0 0 1473 1024" width="17.34375" height="12">
             <path
               d="M1388.020 57.589c-15.543-15.787-37.146-25.569-61.033-25.569s-45.491 9.782-61.023 25.558l-716.054 723.618-370.578-374.571c-15.551-15.769-37.151-25.537-61.033-25.537s-45.482 9.768-61.024 25.527c-15.661 15.865-25.327 37.661-25.327 61.715 0 24.053 9.667 45.849 25.327 61.715l431.659 436.343c15.523 15.814 37.124 25.615 61.014 25.615s45.491-9.802 61.001-25.602l777.069-785.403c15.624-15.868 25.271-37.66 25.271-61.705s-9.647-45.837-25.282-61.717M1388.020 57.589z"
               fill="#6A8FE5" p-id="1025">
               </path>
             </svg>
             </span>
              <p>收货人: {{item.userName}} {{item.isDefault ? '(默认地址)' : ''}}</p>
              <p class="street-name ellipsis">收货地址: {{item.streetName | addressFilter}}</p>

              <p>手机号码: {{item.tel}}</p>
              <div class="operation-section">
                <span class="update-btn" style="font-size:12px" @click="update(item)">修改</span>
                <span class="delete-btn" style="font-size:12px" :data-id="item.addressId" @click="del(item.addressId)">删除</span>
              </div>
            </li>

            <li class="add-address-item" @click="update()">
              <img src="../../../static/svg/jia.svg" alt="">
              <p>使用新地址</p>
            </li>
          </ul>
        </div>
      </y-shelf>
      <!-- 购物清单 -->
      <y-shelf title="购物清单">
        <div slot="content">
          <div class="box-inner ui-cart">
            <div>
              <!--标题-->
              <div class="cart-table-title">
                <span class="name">商品信息</span>
                <span class="subtotal">小计</span>
                <span class="num">数量</span>
                <span class="price">单价</span>
              </div>
              <!--列表-->
              <div class="cart-table" v-for="(item,i) in cartList" :key="i" v-if="item.checked == 'true'">
                <div class="cart-group divide pr" :data-productid="item.productId">
                  <div class="cart-top-items">
                    <div class="cart-items clearfix">
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
                      <!--商品数量-->
                      <div>
                        <!--总价格-->
                        <div class="subtotal" style="font-size: 14px">¥ {{item.salePrice * item.productNum}}</div>
                        <!--数量-->
                        <div class="item-cols-num">
                          <span v-text="item.productNum"></span>
                        </div>
                        <!--价格-->
                        <div class="price">¥ {{item.salePrice}}</div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
            <!-- 合计 -->
            <div class="cart-bottom-bg fix-bottom">
              <div class="fix-bottom-inner">
                <div class="shipping">
                  <div class="shipping-box" style="padding: 0 40px;">
                    <div class="shipping-total shipping-price"><h4
                      class="highlight">应付总额：<em>￥{{checkPrice}}</em>
                    </h4>
                    </div>
                  </div>
                  <y-button class="big-main-btn"
                            :classStyle="submit?'disabled-btn':'main-btn'"
                            style="margin: 0;width: 130px;height: 50px;line-height: 50px;font-size: 16px"
                            :text="submitOrder"
                            @btnClick="_submitOrder">
                  </y-button>
                </div>
              </div>
            </div>
          </div>
        </div>
      </y-shelf>

      <y-popup :open="popupOpen" @close='popupOpen=false' :title="popupTitle">
        <div slot="content" class="md" :data-id="msg.addressId">
          <div>
            <el-input type="text" placeholder="收货人姓名" v-model="msg.userName"></el-input>
          </div>
          <div>
            <el-input type="number" placeholder="手机号码" v-model="msg.tel"></el-input>
          </div>
          <!--      数据源 https://github.com/wecatch/china_regions/tree/master/json    -->
          <!--        TODO 地址模块的同学mysql创建字典表-->
          <!--        <div>-->
          <!--          <input type="text" placeholder="收货地址" v-model="msg.streetName">-->
          <!--        </div>-->
          <div style="display: flex;flex-direction: row">
            <div style="flex: 1;">
              <el-select @change="_handleProvinceChange" v-model="provinceId" placeholder="请选择省份">
                <el-option
                  v-for="(item, index) in provinceList"
                  :key="index"
                  :label="item.name"
                  :value="item.id">
                </el-option>
              </el-select>
            </div>
            <div style="flex: 1;padding-left:4px">
              <el-select @change="_handleCityChange" v-model="cityId" placeholder="请选择市">
                <el-option
                  v-for="(item, index) in cityList"
                  :key="index"
                  :label="item.name"
                  :value="item.id">
                </el-option>
              </el-select>
            </div>
          </div>
          <div style="display: flex;">
            <el-select style="flex: 1" @change="_handleDistrictChange" v-model="districtId" placeholder="请选择区">
              <el-option
                v-for="(item, index) in districtList"
                :key="index"
                :label="item.name"
                :value="item.id">
              </el-option>
            </el-select>
          </div>
          <div>
            <el-input type="text" placeholder="详细地址" @change="_handleAddressChange" v-model="address"></el-input>
          </div>
          <div>
            <el-checkbox class="auto-login" v-model="msg._Default">设为默认</el-checkbox>
          </div>
          <y-button text='保存'
                    class="btn"
                    :classStyle="btnHighlight?'main-btn':'disabled-btn'"
                    @btnClick="save({userId:userId,addressId:msg.addressId,userName:msg.userName,tel:msg.tel,streetName:msg.streetName,_Default:msg._Default})">
          </y-button>
        </div>
      </y-popup>
    </div>
    <y-footer></y-footer>
  </div>
</template>
<script>
  import { getCartList, addressList, addressUpdate, addressAdd, addressDel, productDet, submitOrder } from '/api/goods'
  import YShelf from '/components/shelf'
  import YButton from '/components/YButton'
  import YPopup from '/components/popup'
  import YHeader from '/common/header'
  import YFooter from '/common/footer'
  import cityMap from '/utils/area/city'
  import provinceList from '/utils/area/province'
  import districtMap from '/utils/area/country'
  import Util from '/utils'
  export default {
    data () {
      return {
        cartList: [],
        addList: [],
        addressId: '0',
        popupOpen: false,
        popupTitle: '管理收货地址',
        num: '', // 立刻购买
        productId: '',
        msg: {
          addressId: '',
          userName: '',
          tel: '',
          streetName: '',
          isDefault: false
        },
        userName: '',
        tel: '',
        streetName: '',
        orderTotal: 0,
        submit: false,
        submitOrder: '提交订单',
        // data
        provinceList: [],
        cityList: [],
        districtList: [],

        city: '', // 市
        province: '', // 省
        district: '', // 区
        cityId: null,
        provinceId: null,
        districtId: null,
        address: ''  // 街道
      }
    },
    computed: {
      btnHighlight () {
        let msg = this.msg
        return msg.userName && msg.tel && msg.streetName
      },
      // 选中的总价格
      checkPrice () {
        let totalPrice = 0
        this.cartList && this.cartList.forEach(item => {
          if (item.checked) {
            totalPrice += (item.productNum * item.salePrice)
          }
        })
        this.orderTotal = totalPrice
        return totalPrice
      }
    },
    methods: {
      message (m) {
        this.$message.error({
          message: m
        })
      },
      _handleProvinceChange (provinceId) {
        if (!provinceId) {
          return
        }
        this.province = provinceList.find(p => p.id === provinceId).name
        this.cityId = null
        this.districtId = null
        this.districtList = []

        let cityList = cityMap[provinceId]
        this.cityList = cityList || []
        this.msg.streetName = this.province + '-' + this.city + '-' + this.district + '-' + this.address
      },
      _handleCityChange (cityId) {
        if (!cityId) {
          return
        }
        this.city = this.cityList.find(c => c.id === cityId).name
        this.districtId = null

        let districtList = districtMap[cityId]
        this.districtList = districtList || []
        this.msg.streetName = this.province + '-' + this.city + '-' + this.district + '-' + this.address
      },
      _handleDistrictChange (districtId) {
        if (!districtId) {
          return
        }
        this.district = this.districtList.find(d => d.id === districtId).name
        this.msg.streetName = this.province + '-' + this.city + '-' + this.district + '-' + this.address
      },
      _handleAddressChange () {
        this.msg.streetName = this.province + '-' + this.city + '-' + this.district + '-' + this.address
        // console.log('%c[addressList-_handleAddressChange]', 'color: #63ADD1', this.msg.streetName)
      },
      goodsDetails (id) {
        window.open(window.location.origin + '#/product/' + id)
      },
      _getCartList () {
        getCartList().then(res => {
          this.cartList = res.result
        })
      },
      _addressList () {
        addressList().then(res => {
          let data = res.result
          if (data.length) {
            this.addList = data
            this.addressId = data[0].addressId || '1'
            this.userName = data[0].userName
            this.tel = data[0].tel
            this.streetName = data[0].streetName
          } else {
            this.addList = []
          }
        })
      },
      _addressUpdate (params) {
        addressUpdate(params).then(res => {
          this._addressList()
        })
      },
      _addressAdd (params) {
        addressAdd(params).then(res => {
          if (res.success === true) {
            this._addressList()
          } else {
            this.message(res.message)
          }
        })
      },
      _addressDel (params) {
        addressDel(params).then(res => {
          this._addressList()
        })
      },
      // 提交订单后跳转付款页面
      _submitOrder () {
        this.submitOrder = '提交订单中...'
        this.submit = true
        var array = []
        if (this.addressId === '0') {
          this.message('请选择收货地址')
          this.submitOrder = '提交订单'
          this.submit = false
          return
        }
        if (this.cartList.length === 0) {
          this.message('非法操作')
          this.submitOrder = '提交订单'
          this.submit = false
          return
        }
        for (var i = 0; i < this.cartList.length; i++) {
          if (this.cartList[i].checked) {
            array.push(this.cartList[i])
          }
        }
        let params = {
          tel: this.tel,
          userName: this.userName,
          streetName: this.streetName,
          cartProductDtoList: array,
          addressId: this.addressId,
          orderTotal: this.orderTotal
        }
        submitOrder(params).then(res => {
          if (res.success === true) {
            this.payment(res.result)
          } else {
            this.message(res.message)
            this.submitOrder = '提交订单'
            this.submit = false
          }
        })
      },
      // 付款
      payment (orderId) {
        // 需要拿到地址id
        this.$router.push({
          path: '/order/payment/' + orderId
        })
      },
      // 选择地址
      chooseAddress (addressId, userName, tel, streetName) {
        this.addressId = addressId
        this.userName = userName
        this.tel = tel
        this.streetName = streetName
      },
      // 修改
      update (item) {
        this.popupOpen = true
        if (item) {
          this.popupTitle = '管理收货地址'
          this.msg.userName = item.userName
          this.msg.tel = item.tel
          this.msg.streetName = item.streetName
          this.msg.isDefault = item.isDefault
          this.msg.addressId = item.addressId
          // init 地址选择框
          this._initAddressSelect(item.streetName)
        } else {
          this.popupTitle = '新增收货地址'
          this.msg.userName = ''
          this.msg.tel = ''
          this.msg.streetName = ''
          this.msg.isDefault = false
          this.msg.addressId = ''
        }
      },
      // 保存
      save (p) {
        this.popupOpen = false
        if (p.addressId) {
          this._addressUpdate(p)
        } else {
          delete p.addressId
          this._addressAdd(p)
        }
      },
      // 删除
      del (addressId) {
        this._addressDel({addressId})
      },
      _productDet (productId) {
        productDet({params: {productId}}).then(res => {
          let item = res.result
          item.checked = 'true'
          item.productImg = item.productImageBig
          item.productNum = this.num
          item.productPrice = item.salePrice
          this.cartList.push(item)
        })
      },
      _clearAddressSelect () {
        this.provinceId = null
        this.cityId = null
        this.districtId = null
      },
      _initAddressSelect (streetName) {
        let addressList = !Util.isEmpty(streetName) ? streetName.split('-') : []
        if (addressList.length >= 3) {
          this.province = addressList[0]
          this.city = addressList[1]
          this.district = addressList[2]
          this.address = addressList.length === 4 ? addressList[3] : ''

          // 查找对应省份
          let provinceObj = provinceList.find(p => p.name === this.province)
          if (!Util.isEmpty(provinceObj)) {
            this.provinceId = provinceObj.id
            this.cityList = cityMap[this.provinceId]
            // 查找对应城市
            let cityObj = cityMap[this.provinceId].find(c => c.name === this.city)
            if (!Util.isEmpty(cityObj)) {
              this.cityId = cityObj.id
              this.districtList = districtMap[this.cityId]
              // 查找对应地区
              let districtObj = districtMap[this.cityId].find(d => d.name === this.district)
              if (!Util.isEmpty(districtObj)) {
                this.districtId = districtObj.id
              }
            }
          }
        } else {
          // 老数据直接重置
          this.provinceId = null
          this.cityId = null
          this.districtId = null
        }
      }
    },
    filters: {
      addressFilter (streetName) {
        return streetName ? streetName.replace(new RegExp('-', 'g'), '') : ''
      }
    },
    created () {
      let query = this.$route.params
      if (query.productId && query.num) {
        this.productId = query.productId
        this.num = query.num
        this._productDet(this.productId)
      } else {
        this._getCartList()
      }
      this.provinceList = provinceList
      this.cityList = []
      this.district = []
      this._addressList()
    },
    components: {
      YShelf,
      YButton,
      YPopup,
      YHeader,
      YFooter
    }
  }
</script>
<style lang="scss" rel="stylesheet/scss" scoped>
  // 收货地址
  .address-item-list {
    padding: 30px 13px 0;
    .address {
      padding: 19px 14px 0 19px;
      p {
        line-height: 26px;
      }
    }
    li.checked {
      border-color: #6A8FE5;
      position: relative;
      background: #fff;
      .pa {
        right: 15px;
        top: 18px;
      }
      &:hover {
        background: #fff;
      }
    }
    li {
      position: relative;
      overflow: hidden;
      float: left;
      width: 276px;
      height: 158px;
      margin: 0 0 30px 16px;
      border: 1px solid #E5E5E5;
      border-radius: 3px;
      background: #FAFAFA;
      line-height: 14px;
      text-align: left;
      word-wrap: break-word;
      word-break: normal;
      color: #626262;
      cursor: pointer;
      -moz-user-select: -moz-none;
      -webkit-user-select: none;
      -o-user-select: none;
      user-select: none;
      &:hover {
        background: #F2F2F2;
        .operation-section {
          visibility: visible;
          transform: translate(0, 0);
        }
      }
      &.add-address-item {
        text-align: center;
        display: flex;
        justify-content: center;
        align-items: center;
        flex-direction: column;
        p {
          margin-top: 5px;
        }
      }
      .operation-section {
        visibility: hidden;
        position: absolute;
        left: 0;
        bottom: 0;
        width: 100%;
        height: 28px;
        background: #E1E1E1;
        border-top: 1px solid #E1E1E1;
        transition: all .2s ease;
        transform: translate(0, 29px);
        border-top: 1px solid #E1E1E1;
        display: flex;
        align-items: center;
        justify-content: center;
        z-index: 11;
        span {
          background: #fff;
          display: flex;
          align-items: center;
          justify-content: center;
          flex: 1;
          height: 100%;
          &:hover {
            background: #F2F2F2;
          }
        }

        span + span {
          border-left: 1px solid #E1E1E1;
        }

      }
    }
  }

  .s-content {
    .md {
      > div {
        text-align: left;
        margin-bottom: 15px;
        > input {
          width: 100%;
          height: 38px;
          font-size: 14px;
          padding: 10px 20px;
          border: 1px solid #ccc;
          border-radius: 6px;
          box-shadow: 0 3px 5px -4px rgba(0, 0, 0, .4) inset, -1px 0 3px -2px rgba(0, 0, 0, .1) inset;
          line-height: 46px;
        }
      }
    }

    .btn {
      margin: 0;
      width: 100%;
      height: 43px;
      font-size: 14px;
      line-height: 43px
    }
  }

  .ui-cart {
    img {
      width: 80px;
      height: 80px;
    }

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
      .subtotal {
        font-weight: 700;
      }
      .item-cols-num,
      .price,
      .subtotal {
        overflow: hidden;
        float: right;
        width: 137px;
        text-align: center;
        color: #666;
        line-height: 140px;
      }
      /*数量*/
      .subtotal,
      .item-cols-num {
        padding-top: 50px;
        line-height: 40px;
      }
      .select {
        width: 112px;
        height: 40px;
        padding-top: 4px;
        margin: 0 auto;
        line-height: 40px;
        .down {
          background-position: 0 -60px;
        }
        .down.down-disabled:hover {
          background-position: 0 -300px;
          cursor: not-allowed;
        }
        .num {
          position: relative;
          overflow: hidden;
          display: inline-block;
          width: 36px;
          height: 18px;
          margin: 7px 0 0;
          border: none;
          border-radius: 3px;
          line-height: 18px;
          text-align: center;
          font-size: 14px;
          transition: all .2s ease-out;
        }
      }

    }
    .down.down-disabled {
      background-position: 0 -300px;
      cursor: not-allowed;
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

  .fix-bottom {
    padding: 22px 29px 19px 30px;
    height: 90px;
    width: 100%;
    z-index: 1;
    background-position: center;
    background: #fdfdfd;
    background: -webkit-linear-gradient(#fdfdfd, #f9f9f9);
    background: linear-gradient(#fdfdfd, #f9f9f9);
    border-top: 1px solid #e9e9e9;
    box-shadow: 0 -3px 8px rgba(0, 0, 0, .04);
    .cart-bottom-bg {
      height: 80px;
      border-top: 1px solid #D9D9D9;
      border-radius: 0 0 8px 8px;
    }
    .fix-bottom-inner {
      height: 100%;
      display: flex;
      align-items: center;
      justify-content: flex-end;
    }
    .shipping {
      display: flex;
      align-items: center;
    }
    em {
      display: inline-block;
      position: relative;
      top: 3px;
      margin-top: -4px;
      font-size: 24px;
      color: #d44d44;
      font-weight: 700;
    }
  }

  .attribute, .name p {
    color: #999;
    font-size: 12px;
    padding-top: 4px;
    line-height: 17px;
  }
</style>
<style lang="scss">
  .md .el-input__inner {
    width: 100%;
    height: 36px;
    font-size: 14px;
    padding: 10px 20px;
    border: 1px solid #ccc;
    border-radius: 6px;
    box-shadow: 0 2px 5px -4px rgba(0, 0, 0, .4) inset, -1px 0 3px -2px rgba(0, 0, 0, .1) inset;
    line-height: 36px;
  }
</style>
