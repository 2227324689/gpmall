<template>
  <div class="w">
    <y-shelf title="支付成功" style="text-align: center">
      <div slot="content" class="content">
        <p><i class="el-icon-circle-check icon-success"></i> 恭喜您支付成功，请等待5-30分钟系统确认<br>总共花费 <span class="price"><em style="font-size: 18px">¥</em>{{price}}</span> 元</p>
        <div style="margin-top: 20px;">
          <router-link to="/user">
            <y-button text="查看订单" style="width: 150px;height: 50px;line-height: 48px;font-size: 20px"
                      classStyle="main-btn"></y-button>

          </router-link>
          <router-link to="/home">
            <y-button text="继续选购" style="width: 150px;height: 50px;line-height: 48px;font-size: 20px"
                      classStyle="main-btn"></y-button>

          </router-link>
        </div>
      </div>
    </y-shelf>
  </div>
</template>
<script>
  import YShelf from '/components/shelf'
  import YButton from '/components/YButton'
  export default {
    data () {
      return {
        price: 10
      }
    },
    components: {
      YShelf,
      YButton
    },
    created () {
      this.price = this.$route.query.price
    }
  }
</script>
<style lang="scss" scoped>
  .content {
    padding: 150px 0;
    font-size: 30px;
  }

  .icon-success {
    font-size: 33px;
    color: #01b401;
  }

  span.price {
    color: #d44d44;
    font-weight: 700;
    line-height: 20px;
    text-align: right;
  }
</style>
