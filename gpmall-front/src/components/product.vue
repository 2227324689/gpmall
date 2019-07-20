<template>
  <div class="item" id="product.spu.id">
    <div class="img-box">
      <img :src="product.spu.sku_info[0].ali_image" alt="">
    </div>
    <div class="info">
      <h6 class="ellipsis">{{product.spu.sku_info[0].title}}</h6>
      <p>{{product.spu.sku_info[0].sub_title}}</p>
    </div>
    <p class="price">
      <i>¥</i>
      <span>{{product.spu.price}}</span>
    </p>
    <ul class="dot-list">
      <li></li>
    </ul>
  </div>
</template>
<script>
  export default {
    props: [
      'product'
    ]
  }
</script>
<style scoped lang="scss">
  @import "../assets/style/mixin";

  .item {
    position: relative;
    height: 429px;
    text-align: center;
    img {
      display: block;
      width: 206px;
      height: 206px;
    }
    .img-box {
      @extend %block-center
    }
    .info {
      width: 100%;
      padding: 0 10px;
      h6 {
        overflow: hidden;
        font-size: 16px;
        line-height: 1.2;
        white-space: nowrap;
        text-overflow: ellipsis;
        color: #424242;
      }
      p {
        overflow: hidden;
        padding-top: 7px;
        font-size: 12px;
        line-height: 1.2;
        white-space: nowrap;
        text-overflow: ellipsis;
        color: #b2b2b2;
      }
    }
  }
</style>
