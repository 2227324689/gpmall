<template>
  <div>
    <y-shelf title="账户资料">
      <div slot="content">
        <div class="avatar-box">
          <div class=img-box><img :src="userInfo.info.file" alt=""></div>
          <div class="r-box">
            <h3 style="margin-left: 13px;">修改头像</h3>
            <y-button text="上传头像" classStyle="main-btn" style="margin: 0;" @btnClick="editAvatar()"></y-button>
          </div>
        </div>
        <div class="edit-avatar" v-if="editAvatarShow">
          <y-shelf title="设置头像">
        <span slot="right">
                              <span class="close" @click="editAvatarShow=false">
                        <svg t="1501234940517" class="icon" style="" viewBox="0 0 1024 1024" version="1.1"
                             xmlns="http://www.w3.org/2000/svg" p-id="3014" xmlns:xlink="http://www.w3.org/1999/xlink"
                             width="20" height="20"><path
                          d="M941.576 184.248l-101.824-101.824L512 410.176 184.248 82.424 82.424 184.248 410.168 512l-327.744 327.752 101.824 101.824L512 613.824l327.752 327.752 101.824-101.824L613.832 512z"
                          fill="#cdcdcd" p-id="3015"></path></svg>
                    </span>
        </span>
            <div slot="content" class="content">
              <div class="edit-l">
                <div style="width: 100px;height: 100px;border: 1px solid #ccc;margin-bottom: 20px;overflow: hidden;">
                  <div class="show-preview"
                       :style="{'width': previews.w + 'px','height': previews.h + 'px','overflow': 'hidden','zoom':option.zoom}">
                    <div :style="previews.div">
                      <img :src="option.img"
                           :style="previews.img"
                      >
                    </div>
                  </div>
                </div>
                <div style="padding: 10px 0 ">头像预览</div>
                <div class="btn">
                  <a href="javascript:;">重新选择</a>
                  <input type="file" value="上传头像" @change="upimg($event)"></div>
              </div>
              <div class="edit-r">
                <div>
                  <div class="big" id="cropper-target" v-if="option.img">
                    <vueCropper
                      :img="option.img"
                      @realTime="realTime"
                      ref="cropper"
                      :outputSize="example2.size"
                      :info="example2.info"
                      :canScale="example2.canScale"
                      :autoCrop="example2.autoCrop"
                      :autoCropWidth="example2.width"
                      :autoCropHeight="example2.height"
                      :fixed="example2.fixed"
                    ></vueCropper>
                  </div>
                </div>

              </div>
              <div class="bootom-btn pa">
                <y-button style="width: 140px;height: 40px;line-height: 40px"
                          text="取消"
                          @btnClick="editAvatarShow=false">
                </y-button>
                <y-button style="width: 140px;height: 40px;line-height: 40px"
                          text="确定"
                          classStyle="main-btn"
                          @btnClick="cropper">
                </y-button>
              </div>
            </div>
          </y-shelf>
        </div>
      </div>
    </y-shelf>
  </div>
</template>
<script>
  import YButton from '/components/YButton'
  import { upload } from '/api/index'
  import YShelf from '/components/shelf'
  import vueCropper from 'vue-cropper'
  import { mapState, mapMutations } from 'vuex'
  import { getStore } from '/utils/storage'
  export default {
    data () {
      return {
        imgSrc: '',
        editAvatarShow: false,
        cropContext: '',
        cropperImg: '',
        previews: {},
        option: {
          img: '',
          zoom: 0
        },
        fixedNumber: [1, 1],
        example2: {
          info: true,
          size: 1,
          canScale: false,
          autoCrop: true,
          // 只有自动截图开启 宽度高度才生效
          autoCropWidth: 300,
          autoCropHeight: 250,
          // 开启宽度和高度比例
          fixed: true
        },
        userId: '',
        token: ''
      }
    },
    computed: {
      ...mapState(['userInfo'])
    },
    methods: {
      ...mapMutations([
        'RECORD_USERINFO'
      ]),
      message (m) {
        this.$message(m)
      },
      messageSuccess (m) {
        this.$message({
          message: m,
          type: 'success'
        })
      },
      messageFail (m) {
        this.$message.error({
          message: m
        })
      },
      upimg (e) {
        var file = e.target.files[0]
        if (file.size > 1048576) {
          this.messageFail('图片大小不得超过1Mb')
          return false
        }
        if (!/\.(gif|jpg|jpeg|png|bmp|GIF|JPG|PNG)$/.test(e.target.value)) {
          this.messageFail('图片类型仅支持.gif,jpeg,jpg,png,bmp')
          return false
        }
        var reader = new FileReader()
        reader.readAsDataURL(file)
        reader.onload = (e) => {
          this.option.img = e.target.result
        }
      },
      cropper () {
        this.message('上传中...')
        if (this.option.img) {
          this.$refs.cropper.getCropData((data) => {
            this.imgSrc = data
            upload({userId: this.userId, token: this.token, imgData: data}).then(res => {
              if (res.success === true) {
                let path = res.result
                let info = this.userInfo
                info.file = path
                this.RECORD_USERINFO({info: info})
                this.editAvatarShow = false
                this.messageSuccess('上传成功')
              } else {
                this.messageFail(res.message)
              }
            })
          })
        } else {
          this.messageFail('别玩我啊 先选照骗')
        }
      },
      editAvatar () {
        this.editAvatarShow = true
      },
      realTime (data) {
        this.previews = data
        let w = 100 / data.w
        this.option.zoom = w
      }
    },
    created () {
      this.userId = getStore('userId')
      this.token = getStore('token')
    },
    components: {
      YButton,
      YShelf,
      vueCropper
    }
  }
</script>
<style lang="scss" scoped>
  @import "../../../assets/style/mixin";

  .avatar-box {
    height: 124px;
    display: flex;
    margin: 0 30px 30px;
    border-bottom: #dadada solid 1px;
    line-height: 30px;
    display: flex;
    align-items: center;
    .img-box {
      @include wh(80px);
      border-radius: 5px;
      overflow: hidden;
    }
    img {
      display: block;
      @include wh(100%)
    }
    .r-box {
      margin-left: 20px;
      h3 {
        font-size: 18px;
        font-weight: 400;
        color: #333;
      }
    }
  }

  // 修改头像
  .edit-avatar {
    z-index: 9999;
    position: fixed;
    left: 0;
    right: 0;
    top: 0;
    bottom: 0;
    @include wh(100%);
    background-color: rgba(0, 0, 0, .5);
    @extend %block-center;
    .content {
      display: flex;
      padding: 45px 100px 0;
    }
    > div {
      box-sizing: content-box;
      @include wh(700px, 500px);
      margin: 0;
    }
    .btn {
      width: 80px;
      height: 30px;
      margin-left: 10px;
      position: relative;
      text-align: center;
      line-height: 30px;
      text-shadow: rgba(255, 255, 255, .496094) 0 1px 0;
      border: 1px solid #E6E6E6;
      border-radius: 10px;
      &:hover {
      }
      a {
        color: #666;
        display: block;
        @include wh(100%);
      }
    }
    input[type=file] {
      position: absolute;
      right: 0;
      left: 0;
      top: 0;
      opacity: 0;
      width: 80px;
      height: 30px;
      cursor: pointer;
      box-sizing: border-box;
      border: 15px solid #000;
      overflow: hidden;
    }
    .edit-l {
      width: 100px;
      text-align: center;
    }
    .edit-r {
      margin-left: 20px;
      flex: 1;
      > div {
        border: 1px solid #ccc;
        width: 320px;
        height: 320px;
      }
    }
  }

  .image-container {
    width: 100px;
    height: 100px;
    border: 1px solid #ccc;
  }

  .close {
    position: absolute;
    right: 10px;
    top: 0;
    bottom: 0;
    padding: 0 10px;
    @extend %block-center;
    &:hover {
      svg {
        transition: all 1s;
        transform: rotate(360deg);
        transform-origin: 50% 50%;
      }
      path {
        fill: #8a8a8a;
      }
    }
  }

  .big {
    display: block;
    width: 320px;
    height: 320px;
  }

  .bootom-btn {
    padding: 0 15px;
    border-top: 1px solid #E6E6E6;
    bottom: 0;
    height: 60px;
    right: 0;
    left: 0;
    display: flex;
    align-items: center;
    justify-content: space-between;
  }
</style>
