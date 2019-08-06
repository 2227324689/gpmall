<template>
  <div class="login v2">
    <div class="wrapper">
      <div class="dialog dialog-shadow" style="display: block; margin-top: -362px;">
        <div class="registered">
          <h4>注册 咕泡商城 账号</h4>
          <div class="content" style="margin-top: 20px;">
            <ul class="common-form">
              <li class="username border-1p">
                <div style="margin-top: 40px;" class="input">
                  <input type="text"
                         v-model="registered.userName" placeholder="账号"
                         @keyup="registered.userName=registered.userName.replace(/[^\w\.\/]/ig,'')">
                </div>
              </li>
              <li>
                <div class="input">
                  <input type="password"
                         v-model="registered.userPwd"
                         placeholder="密码">
                </div>
              </li>
              <li>
                <div class="input">
                  <input type="password"
                         v-model="registered.userPwd2"
                         placeholder="重复密码">
                </div>
              </li>
              <li>
                <div class="input">
                  <input type="text" v-model="registered.captcha" placeholder="验证码" style="width:650px;"/>
                  &nbsp;&nbsp;&nbsp;
                  <img id="imageCode" :src="imageCode" @click="init_geetest()"/>
                </div>
              </li>
            </ul>
            <el-checkbox class="agree" v-model="agreement">
              我已阅读并同意遵守
              <a @click="open('法律声明','此站点仅作为学习使用，承担不起任何法律问题')">法律声明</a> 和
              <a @click="open('隐私条款','本网站将不会严格遵守有关法律法规和本隐私政策所载明的内容收集、使用您的信息')">隐私条款</a>
            </el-checkbox>
            <div style="margin-bottom: 30px;">
              <y-button
                :classStyle="registered.userPwd&&registered.userPwd2&&registered.userName&&registered.captcha&&registxt==='注册'?'main-btn':'disabled-btn'"
                :text="registxt"
                style="margin: 0;width: 100%;height: 40px;font-size: 15px;line-height: 40px"
                @btnClick="regist"
              >
              </y-button>
            </div>
            <div class="border" style="margin-bottom: 10px;"></div>
            <ul class="common-form pr">
              <!-- <li class="pa" style="left: 0;top: 0;margin: 0;color: #d44d44">{{registered.errMsg}}</li> -->
              <li style="text-align: center;line-height: 48px;margin-bottom: 0;font-size: 15px;color: #999;">
                <span>如果您已拥有 <span style="color:royalblue">咕泡商城</span> 账号，则可在此</span>
                <a href="javascript:;"
                   style="margin: 0 5px"
                   @click="toLogin">登录</a>
              </li>
            </ul>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script src="../../../static/geetest/gt.js"></script>
<script>
import YFooter from '/common/footer'
import YButton from '/components/YButton'
import { register, initKaptcha } from '/api/index.js'
require('../../../static/geetest/gt.js')
// var captcha
export default {
  data () {
    return {
      cart: [],
      loginPage: true,
      ruleForm: {
        userName: '',
        userPwd: '',
        errMsg: ''
      },
      registered: {
        userName: '',
        userPwd: '',
        userPwd2: '',
        errMsg: '',
        captcha: ''
      },
      imageCode: '',
      agreement: false,
      registxt: '注册',
      statusKey: ''
    }
  },
  computed: {
    count () {
      return this.$store.state.login
    }
  },
  methods: {
    open (t, m) {
      this.$notify.info({
        title: t,
        message: m
      })
    },
    messageSuccess () {
      this.$message({
        message: '恭喜您，注册成功！赶紧登录体验吧',
        type: 'success'
      })
    },
    message (m) {
      this.$message.error({
        message: m
      })
    },
    toLogin () {
      this.$router.push({
        path: '/login'
      })
    },
    regist () {
      this.registxt = '注册中...'
      let userName = this.registered.userName
      let userPwd = this.registered.userPwd
      let userPwd2 = this.registered.userPwd2
      let captcha = this.registered.captcha
      if (!userName || !userPwd || !userPwd2) {
        this.message('账号密码不能为空!')
        this.registxt = '注册'
        return false
      }
      if (userPwd2 !== userPwd) {
        this.message('两次输入的密码不相同!')
        this.registxt = '注册'
        return false
      }
      if (!captcha) {
        this.message('请输入验证码')
        this.registxt = '注册'
        return false
      }
      if (!this.agreement) {
        this.message('您未勾选同意我们的相关注册协议!')
        this.registxt = '注册'
        return false
      }
      register({
        userName,
        userPwd,
        captcha}).then(res => {
          if (res.success === true) {
            this.messageSuccess()
            this.toLogin()
          } else {
            this.message(res.message)
            this.registxt = '注册'
            this.init_geetest()
            this.registered.captcha = ''
            return false
          }
        })
    },
    init_geetest () {
      initKaptcha().then(res => {
        this.imageCode = 'data:image/gif;base64,' + res.result
      })
    }
  },
  mounted () {
    this.init_geetest()
  },
  components: {
    YFooter,
    YButton
  }
}
</script>
<style lang="scss" rel="stylesheet/scss" scoped>
* {
  box-sizing: content-box;
}

.login {
  overflow-x: hidden;
  overflow-y: hidden;
  .input {
    height: 46px;
    display: flex;
    align-items: center;
    input {
      font-size: 14px;
      width: 100%;
      height: 100%;
      padding: 10px 15px;
      box-sizing: border-box;
      border: 1px solid #ccc;
      border-radius: 6px;
    }
  }
  .wrapper {
    background: url(/static/images/bg_9b9dcb65ff.png) repeat;
    background-size: 100px;
    min-height: 800px;
    min-width: 630px;
  }
}

.v2 .dialog {
  width: 450px;
  border: 1px solid #dadada;
  border-radius: 10px;
  top: 50%;
  left: 50%;
  margin-left: -225px;
  position: absolute;
  .title {
    background: linear-gradient(#fff, #f5f5f5);
    height: auto;
    overflow: visible;
    box-shadow: 0 1px 4px rgba(0, 0, 0, 0.08);
    position: relative;
    background-size: 140px;
    background-position: top center;
    background-repeat: no-repeat;
    height: 92px;
    margin: 23px 0 50px;
    padding: 75px 0 0;
    box-shadow: none;
    h4 {
      padding: 0;
      text-align: center;
      color: #666;
      border-bottom: 1px solid #dcdcdc;
      -webkit-box-shadow: none;
      -moz-box-shadow: none;
      box-shadow: none;
      font-weight: 700;
      position: absolute;
      bottom: 0;
      width: 100%;
      text-align: center;
      margin: 0;
      padding: 0;
      border-bottom: 0;
      -webkit-box-shadow: none;
      -moz-box-shadow: none;
      box-shadow: none;
      line-height: 1em;
      height: auto;
      color: #333;
      font-weight: 400;
    }
  }
  .content {
    padding: 0 40px 22px;
    height: auto;
    .common-form {
      li {
        clear: both;
        margin-bottom: 15px;
        position: relative;
      }
    }
  }
}

.dialog-shadow,
.v2 .bbs .dialog-shadow,
.v2 .dialog-shadow {
  -webkit-box-shadow: 0 9px 30px -6px rgba(0, 0, 0, 0.2),
    0 18px 20px -10px rgba(0, 0, 0, 0.04), 0 18px 20px -10px rgba(0, 0, 0, 0.04),
    0 10px 20px -10px rgba(0, 0, 0, 0.04);
  -moz-box-shadow: 0 9px 30px -6px rgba(0, 0, 0, 0.2),
    0 18px 20px -10px rgba(0, 0, 0, 0.04), 0 18px 20px -10px rgba(0, 0, 0, 0.04),
    0 10px 20px -10px rgba(0, 0, 0, 0.04);
  box-shadow: 0 9px 30px -6px rgba(0, 0, 0, 0.2),
    0 18px 20px -10px rgba(0, 0, 0, 0.04), 0 18px 20px -10px rgba(0, 0, 0, 0.04),
    0 10px 20px -10px rgba(0, 0, 0, 0.04);
}

@media screen and (min-width: 737px),
  screen and (-webkit-max-device-pixel-ratio: 1.9) and (max-width: 736px) and (min-device-width: 737px) {
  .wrapper {
    background: url(/static/images/con-bg_04f25dbf8e.jpg) repeat-x;
    position: absolute;
    top: 0;
    bottom: 0;
    left: 0;
    right: 0;
  }
  .dialog {
    background: url(/static/images/dialog-gray-bg.png) #fff bottom repeat-x;
    border-radius: 12px;
    display: none;
    margin: -163px 0 0 -218px;
    width: 436px;
    position: fixed;
    left: 50%;
    top: 50%;
  }
  .dialog .title h4 {
    border-bottom: #d1d1d1 solid 1px;
    box-shadow: 0 2px 6px #d1d1d1;
    color: #666;
    font-size: 20px;
    height: 61px;
    line-height: 61px;
    padding: 0 0 0 35px;
  }
  .common-form li {
    clear: both;
    margin-bottom: 15px;
    position: relative;
  }
  .auto-login {
    position: absolute;
    top: 0px;
    left: 2px;
    color: #999;
  }
  .register {
    padding: 1px 10px 0;
    border-right: 1px solid #ccc;
  }
  .border {
    margin-top: 10px;
    border-bottom: 1px solid #ccc;
  }
  .other {
    margin: 20px 5px 0 0;
    width: auto;
    color: #bbb;
    font-size: 12px;
    cursor: default;
    color: #999;
  }
  .footer {
    display: flex;
    flex-direction: row;
  }
  .agree {
    margin-bottom: 30px;
    color: #999;
  }
}

.registered {
  h4 {
    background-image: -webkit-linear-gradient(#fff, #f1f1f1);
    background-image: linear-gradient(#fff, #f1f1f1);
    padding: 0;
    text-align: center;
    color: #666;
    border-bottom: 1px solid #dcdcdc;
    -webkit-box-shadow: none;
    -moz-box-shadow: none;
    box-shadow: none;
    font-weight: 700;
    font-size: 18px;
    height: 60px;
    line-height: 60px;
    border-radius:20px 10px 0px 0;
  }
}

#wait {
  text-align: left;
  color: #999;
  margin: 0;
}
</style>
