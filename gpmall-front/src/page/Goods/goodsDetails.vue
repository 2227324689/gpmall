<!--商品详情-->
<template>
  <div class="w store-content">
    <div class="gray-box">
      <div class="gallery-wrapper">
        <div class="gallery">
          <div class="thumbnail">
            <ul>
              <li v-for="(item,i) in small" :key="i" :class="{on:big===item}" @click="big=item">
                <img v-lazy="item" :alt="product.productName">
              </li>
            </ul>
          </div>
          <div class="thumb">
            <div class="big">
              <img :src="big" :alt="product.productName">
            </div>
          </div>
        </div>
      </div>
      <!--右边-->
      <div class="banner">
        <div class="sku-custom-title">
          <h4>{{product.productName}}</h4>
          <h6>
            <span>{{product.subTitle}}</span>
            <span class="price">
              <em>¥</em><i>{{product.salePrice.toFixed(2)}}</i></span>
          </h6>
        </div>
        <div class="num">
          <span class="params-name">数量</span>
          <buy-num @edit-num="editNum" :limit="Number(product.limitNum)"></buy-num>
        </div>
        <div class="buy">
          <y-button text="加入购物车"
                    @btnClick="addCart(product.productId,product.salePrice,product.productName,product.productImageBig)"
                    classStyle="main-btn"
                    style="width: 145px;height: 50px;line-height: 48px"></y-button>
          <y-button text="现在购买"
                    @btnClick="checkout(product.productId)"
                    style="width: 145px;height: 50px;line-height: 48px;margin-left: 10px"></y-button>
        </div>
      </div>
    </div>
    <!--产品信息-->
    <el-tabs v-model="activeTab" @tab-click="_handleTabClick" style="background: #fff;border-radius: 5px;padding: 0 50px">
      <el-tab-pane label="产品信息" name="itemInfo">
        <div class="item-info" style="border-radius: 5px">
          <div>
            <div class="img-item" v-if="productMsg">
              <div v-html="productMsg">{{ productMsg }}</div>
            </div>
            <div class="no-info" v-else>
              <img src="/static/images/no-data.png">
              <br>
              该商品暂无内容数据
            </div>
          </div>
        </div>
      </el-tab-pane>
      <el-tab-pane label="评论" name="comment">
        <span slot="label">评论（{{totalCommentCount}})</span>

        <div style="min-height: 300px">
          <div style="display: flex;justify-content: center;padding-top: 130px" v-if="!commentList || commentList.length === 0">
            <span style="font-size: 20px">当前商品无评价</span>
          </div>
          <div v-else>

          </div>
        </div>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>
<script>
  import { productDet, addCart, getAllComments, getAllCommentsCount } from '/api/goods'
  import { mapMutations, mapState } from 'vuex'
  import YShelf from '/components/shelf'
  import BuyNum from '/components/buynum'
  import YButton from '/components/YButton'
  import { getStore } from '/utils/storage'
  export default {
    data () {
      return {
        productMsg: {},
        small: [],
        big: '',
        product: {
          salePrice: 0
        },
        productNum: 1,
        userId: '',
        // form
        pageForm: {
          page: 0,
          size: 10
        },

        // data
        activeTab: 'itemInfo',
        totalCommentCount: 0,
        commentList: [],
        commentType: null
      }
    },
    computed: {
      ...mapState(['login', 'showMoveImg', 'showCart'])
    },
    methods: {
      ...mapMutations(['ADD_CART', 'ADD_ANIMATION', 'SHOW_CART']),
      _productDet (productId) {
        productDet({params: {productId}}).then(res => {
          let result = res.result
          this.product = result
          this.productMsg = result.detail || ''
          this.small = result.productImageSmall
          this.big = this.small[0]
        })
      },
      _productCommentCount (productId) {
        let params = {
          productId,
          type: this.commentType
        }
        getAllCommentsCount({params: params}).then(res => {
          let result = res.result
          console.log('%c[goodsDetails-res]', 'color: #63ADD1', result)
        })
      },
      _productCommentList (productId) {
        let params = {
          productId,
          type: this.commentType,
          page: this.pageForm.page,
          size: this.pageForm.size
        }
        getAllComments({params: params}).then(res => {
          let result = res.result
          console.log('%c[goodsDetails-res]', 'color: #63ADD1', result)
        })
      },
      _handleTabClick (tabComponent) {
        let {name} = tabComponent
        if (name === 'comment') {
          if (this.totalCommentCount !== 0) {
            this.pageForm.page = 0
            this._productCommentList(this.$route.params.productId)
          }
        }
      },

      addCart (id, price, name, img) {
        if (!this.showMoveImg) {     // 动画是否在运动
          if (this.login) { // 登录了 直接存在用户名下
            addCart({userId: this.userId, productId: id, productNum: this.productNum}).then(res => {
              // 并不重新请求数据
              this.ADD_CART({
                productId: id,
                salePrice: price,
                productName: name,
                productImg: img,
                productNum: this.productNum
              })
            })
          } else { // 未登录 vuex
            this.ADD_CART({
              productId: id,
              salePrice: price,
              productName: name,
              productImg: img,
              productNum: this.productNum
            })
          }
          // 加入购物车动画
          var dom = event.target
          // 获取点击的坐标
          let elLeft = dom.getBoundingClientRect().left + (dom.offsetWidth / 2)
          let elTop = dom.getBoundingClientRect().top + (dom.offsetHeight / 2)
          // 需要触发
          this.ADD_ANIMATION({moveShow: true, elLeft: elLeft, elTop: elTop, img: img})
          if (!this.showCart) {
            this.SHOW_CART({showCart: true})
          }
        }
      },
      checkout (productId) {
        this.$router.push({path: '/checkout/' + productId + '/' + this.productNum})
      },
      editNum (num) {
        this.productNum = num
      }
    },
    components: {
      YShelf, BuyNum, YButton
    },
    watch: {
      $route (to, from) {
        if (to.fullPath.includes('/product/')) {
          let id = this.$route.params.productId
          this._productDet(id)
          this.userId = getStore('userId')
        }
      }
    },
    created () {
      let id = this.$route.params.productId
      this._productDet(id)
      this._productCommentCount(id)
      this.userId = getStore('userId')
    }
  }
</script>
<style lang="scss" scoped>
  @import "../../assets/style/mixin";

  .store-content {
    clear: both;
    width: 1220px;
    min-height: 600px;
    padding: 0 0 25px;
    margin: 0 auto;
  }

  .gray-box {
    display: flex;
    padding: 60px;
    margin: 20px 0;
    .gallery-wrapper {
      .gallery {
        display: flex;
        width: 540px;
        .thumbnail {
          li:first-child {
            margin-top: 0px;
          }
          li {
            @include wh(80px);
            margin-top: 10px;
            padding: 12px;
            border: 1px solid #f0f0f0;
            border: 1px solid rgba(0, 0, 0, .06);
            border-radius: 5px;
            cursor: pointer;
            &.on {
              padding: 10px;
              border: 3px solid #ccc;
              border: 3px solid rgba(0, 0, 0, .2);
            }
            img {
              display: block;
              @include wh(100%);
            }
          }
        }
        .thumb {
          .big {
            margin-left: 20px;
          }
          img {
            display: block;
            @include wh(440px)
          }
        }
      }
    }
    // 右边
    .banner {
      width: 450px;
      margin-left: 10px;
      h4 {
        font-size: 24px;
        line-height: 1.25;
        color: #000;
        margin-bottom: 13px;
      }
      h6 {
        font-size: 14px;
        line-height: 1.5;
        color: #bdbdbd;
        display: flex;
        align-items: center;
        justify-content: space-between;
      }
      .sku-custom-title {
        overflow: hidden;
        padding: 8px 8px 18px 10px;
        position: relative;
      }
      .params-name {
        padding-right: 20px;
        font-size: 14px;
        color: #8d8d8d;
        line-height: 36px;
      }
      .num {
        padding: 29px 0 8px 10px;
        border-top: 1px solid #ebebeb;
        display: flex;
        align-items: center;
      }
      .buy {
        position: relative;
        border-top: 1px solid #ebebeb;
        padding: 30px 0 0 10px;
      }
    }
  }

  .item-info {

    .gray-box {
      padding: 0;
      display: block;
    }
    .img-item {
      width: 1220px;
      // padding: 1vw;
      text-align: center;
      img {
        width: 100%;
        height: auto;
        display: block;
      }
    }
  }

  .no-info {
    padding: 200px 0;
    text-align: center;
    font-size: 30px;
  }

  .price {
    display: block;
    color: #d44d44;
    font-weight: 700;
    font-size: 16px;
    line-height: 20px;
    text-align: right;
    i {
      padding-left: 2px;
      font-size: 24px;
    }
  }
</style>
