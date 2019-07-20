<template>
  <div>
    <y-shelf title="以旧换新">
      <div slot="content">
        <div style="padding: 100px 0;text-align: center">
          <img src="/static/images/smile.png">
          <br>
          <span class="support">请问你拿什么和我换新</span>
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
