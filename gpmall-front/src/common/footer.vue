<template>
  <footer>
    <div id="footerCode"></div>
    <div class="float-fixed"></div>
    <div id="footer">
      <div class="footer-contert">
        <div class="footer-content-logo"><img src="//cdn.gupaoedu.com/common/images/nav_logo.png" alt="底部LOGO"> <span>做技术人的指路明灯，职场生涯的精神导师</span>
        </div>
        <div class="footer-content-aboutUs">
          <ul>
            <li>关于我们</li>
            <li><a href="http://www.gupaoedu.com/about.html?con=xueyjianjie">学院简介</a></li>
            <li><a href="http://www.gupaoedu.com/about.html?con=lianxiwomen">联系我们</a></li>
            <li><a href="http://www.gupaoedu.com/partner.html?con=chengpycai20190422">加入我们</a></li>
          </ul>
        </div>
        <div class="footer-content-service">
          <ul>
            <li>服务与支持</li>
            <li><a href="http://www.gupaoedu.com/partner.html?20190422">讲师招募</a></li>
            <li><a href="http://www.gupaoedu.com/partner.html?con=shangwuhez20190422">商务合作</a></li>
            <li><a @click="open1()">法律声明</a></li>
          </ul>
        </div>
        <div class="footer-content-img">
          <div class="footer-content-img-wx"><span><img src="//cdn.gupaoedu.com/common/images/foot-weix.png"
                                                        alt="微信"></span> <span>官方微信</span></div>
          <div class="footer-content-img-wb"><a href="//weibo.com/gupaoedu?is_hot=1" target="_blank"> <span
            class="weibo"><img src="//cdn.gupaoedu.com/common/images/foot-web.png" alt="微博"></span> <span>官方微博</span>
          </a></div>
        </div>
      </div>
      <div class="footer-copy"> 本站仅用于学习交流使用，前端框架是在 <a href="http://xmall.exrick.cn/" target="_blank">xmall</a>
        基础之上做的重构，在此表示感谢</div>
    </div>
  </footer>
</template>
<script>
  import YButton from '/components/YButton'
  export default {
    data () {
      return {
      }
    },
    methods: {
      open1 () {
        this.$notify.info({
          title: '法律声明',
          message: '此仅为个人练习开源模仿项目，仅供学习参考，承担不起任何法律问题'
        })
      },
      open2 () {
        this.$notify.info({
          title: '隐私条款',
          message: '本网站将不会严格遵守有关法律法规和本隐私政策所载明的内容收集、使用您的信息'
        })
      },
      open3 () {
        this.$notify({
          title: '离线帮助',
          message: '没人会帮助你，请自己靠自己',
          type: 'warning'
        })
      },
      open4 () {
        this.$notify.info({
          title: '支付方式',
          message: '支持支付宝、微信等方式捐赠'
        })
      },
      open5 () {
        this.$notify({
          title: '送货政策',
          message: '本网站所有商品购买后不会发货，将用作捐赠',
          type: 'warning'
        })
      }
    },
    components: {
      YButton
    }
  }
</script>
<style lang="scss" rel="stylesheet/scss" scoped>
  .footer {
    padding: 50px 0 20px;
    border-top: 1px solid #e6e6e6;
    background: #fafafa;
    margin-top: 60px;
    height: 350px;
    display: flex;
    flex-direction: column;
    align-items: center;
  }

  .container {
    width: 1220px;
  }
  #footer{
    width:100%;
    padding: 50px 0 20px;
    border-top: 1px solid #e6e6e6;
    background: #fafafa;
    margin-top: 60px;
    height: 350px;
    display: flex;
    flex-direction: column;
    align-items: center;
  }
  #footer>.footer-contert{
    width:1200px;
    margin:auto;
    overflow:hidden;
    padding-bottom:40px;
    /*height:50px;*/
  }
  #footer>.footer-contert div{
    float:left;
  }
  .footer-content-logo{
    margin-right:120px;
  }
  .footer-content-logo>span{
    display:block;
    color:#5e6062;
    font-size:14px;
    margin-top:30px;
  }
  .footer-content-aboutUs{
    margin-right:170px;
  }
  .footer-content-aboutUs>ul li{
    font-size:18px;
    color:#5e6062;
  }
  .footer-content-aboutUs>ul li:first-child{
    margin-bottom:33px;
  }
  .footer-content-aboutUs>ul li>a{
    color:#5e6062;
    font-size:14px;
  }

  .footer-content-service>ul li{
    font-size:18px;
    color:#5e6062;
  }
  .footer-content-service>ul li:first-child{
    margin-bottom:33px;
  }
  .footer-content-service>ul li>a{
    color:#5e6062;
    font-size:14px;
  }

  #footer>.footer-contert>.footer-content-img{
    float:right;
  }
  #footer>.footer-contert>.footer-content-img span{
    display:block;
    color:#5e6062;
    text-align:center;
    font-size:14px;
  }
  #footer>.footer-contert>.footer-content-img span.weibo{
    border-radius: 10px;
    overflow: hidden;
    width: 80px;
    height: 80px;
  }
  #footer>.footer-contert>.footer-content-img>.footer-content-img-wx{
    margin-right:60px;
  }
  .footer-copy{
    width:100%;
    height:52px;
    text-align:center;
    line-height:52px;
    color:#5e6062;
    border-top: 1px solid #eaeaea;
    font-size:14px;
  }
  .footer-copy>a{
    color:#5e6062;
  }
</style>
