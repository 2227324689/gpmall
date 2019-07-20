<template>
  <input :type="inputType"
         readonly
         :class="classStyle"
         @click="btnClick($event)"
         :disabled="classStyle==='disabled-btn'"
         :value="text">
</template>
<script>
  export default {
    props: {
      text: {
        type: [String, Number],
        default: '一颗小按钮'
      },
      inputType: {
        type: [String],
        default: 'button'
      },
      classStyle: {
        type: String,
        default: 'default-btn'
      }
    },
    methods: {
      btnClick (event) {
        this.$emit('btnClick', event)
      }
    }
  }
</script>
<style lang="scss" rel="stylesheet/scss" scoped>
  @import "../assets/style/theme";

  %style {
    width: 100px;
    height: 30px;
    line-height: 28px;
    vertical-align: middle;
  }

  @mixin color($a,$b,$c) {
    border: 1px solid $a;
    border-radius: 4px;
    font-size: 12px;
    color: $b;
    background-color: $c;
  }

  input {
    display: inline-block;
    cursor: pointer;
    text-align: center;
    /*> span {*/
    /*user-select: none;*/
    /*display: inline-block;*/
    /*width: 100%;*/
    /*height: 100%;*/
    /*}*/
  }

  /*灰色的按钮*/
  .gray-btn {
    border: 1px solid #d5d5d5;
    color: #646464;
  }

  // 默认按钮
  .default-btn {
    @include color(#e1e1e1, #646464, #f9f9f9);
    @extend %style;
    background-image: -webkit-linear-gradient(top, $cf, #f9f9f9);
    background-image: linear-gradient(180deg, $cf, #f9f9f9);
    &:hover {
      background-color: #eee;
      background-image: -webkit-linear-gradient(top, #f5f5f5, #eee);
      background-image: linear-gradient(180deg, #f5f5f5, #eee);
    }
  }

  // 主色按钮
  .main-btn {
    @include color(#5c81e3, #fff, #678ee7);
    background-image: -webkit-linear-gradient(top, #678ee7, #5078df);
    background-image: linear-gradient(180deg, #678ee7, #5078df);
    @extend %style;
    &:hover {
      background-color: #6c8cd4;
      background-image: -webkit-linear-gradient(top, #6c8cd4, #4769c2);
      background-image: linear-gradient(180deg, #6c8cd4, #4769c2);
    }
  }

  // 禁用
  .disabled-btn {
    cursor: not-allowed;
    @include color(#afafaf, $cf, #a9a9a9);
    @extend %style;
    background-image: -webkit-linear-gradient(top, #b8b8b8, #a9a9a9);
    background-image: linear-gradient(180deg, #b8b8b8, #a9a9a9);
  }

</style>
