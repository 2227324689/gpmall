<template>
    <div class="popup" v-if="open">
        <div class="mask">
            <div class="content">
                <div class="topbar">
                    <div class="dialog-tit">
                    <h4>{{title}}</h4>
                    </div>
                    <span class="close" @click="close">
                        <svg t="1501234940517" class="icon" style="" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="3014" xmlns:xlink="http://www.w3.org/1999/xlink" width="20" height="20"><path d="M941.576 184.248l-101.824-101.824L512 410.176 184.248 82.424 82.424 184.248 410.168 512l-327.744 327.752 101.824 101.824L512 613.824l327.752 327.752 101.824-101.824L613.832 512z" fill="#cdcdcd" p-id="3015"></path></svg>
                    </span>
                </div>
                <div class="s-content">
                    <slot name="content"></slot>
                </div>
                
            </div>
        </div>
    </div>
</template>
<script>
export default {
  props: {
    open: {
      type: Boolean,
      default: false
    },
    title: {
      type: String,
      default: '管理收货地址'
    }
  },
  methods: {
    close () {
      this.$emit('close')
    }
  }
}
</script>

<style lang="scss" scoped>
.popup {
    position: fixed;
    top: 0;
    left: 0;
    bottom: 0;
    right: 0;
    z-index: 1000;
    text-align: center;
    transition: opacity .2s ease-in;
    .mask {
        display: flex;
        justify-content: center;
        align-items: center;
        position: absolute;
        width: 100%;
        height: 100%;
        background: rgba(0, 0, 0, .5);
    }
    .content {
        width: 30%;
        background: #fff;
        border-radius: 10px;
    }
    .topbar {
        position: relative;
        z-index: 10;
        overflow: hidden;
        width: 100%;
        height: 60px;
        background: #F5F5F5;
        background: linear-gradient(#FFF,#F5F5F5);
        border-bottom: 1px solid #DCDCDC;
        border-radius: 10px 10px 0 0;
        box-shadow: 2px 0 5px rgba(0,0,0,.1);
    }
    .dialog-tit {
        height: 60px;
        padding: 0 15px;
        line-height: 60px;
         h4 {
        text-align: center;
        font-size: 18px;
        font-weight: 400;
        color: #666;
        }
    }
    .s-content{
        padding: 30px 15px;
    }
    .close{
        position: absolute;
        right: 10px;
        top: 50%;
        transform:translateY(-50%);
        width: 20px;
        height: 20px;
        &:hover{
            svg{
            transition: all 1s;
            transform: rotate(360deg);
            transform-origin:50% 50%;
            }   
            path{
                fill: #8a8a8a;
            }
        }
    }
}
</style>

