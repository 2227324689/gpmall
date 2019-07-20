<template>
  <div>
    <y-shelf title="售后服务">
      <div slot="content">
        <div style="padding: 100px 0;text-align: center">
          <img src="/static/images/support.png">
          <br>
          <span class="support">如有疑问请联系邮箱1012139570@qq.com</span>
        </div>
      </div>
    </y-shelf>
  </div>
</template>
<script>
  import YShelf from '/components/shelf'
  export default {
    components: {
      YShelf
    }
  }
</script>
<style lang="scss" scoped>
  .support {
    line-height: 2em;
    font-size: 22px;
    color: #999;
  }
</style>
