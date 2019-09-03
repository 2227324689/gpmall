<template>
  <div>
    <y-shelf title="收货地址">
      <span slot="right"><y-button text="添加收货地址" style="margin: 0" @btnClick="addNewAddress()"></y-button></span>
      <div slot="content">
        <!--标题-->
        <div class="table-title">
          <span class="name">姓名</span> <span class="address">详细地址</span> <span class="tel">电话</span>
        </div>
        <div v-if="addList.length">
          <div class="address-item" v-for="(item,i) in addList" :key="i">
            <div class="name">{{item.userName}}</div>
            <div class="address-msg">{{item.streetName | addressFilter}}</div>
            <div class="telephone">{{item.tel}}</div>
            <div class="defalut">
              <a @click="changeDef(item)"
                 href="javascript:;"
                 v-text="item.isDefault?'( 默认地址 )':'设为默认'"
                 :class="{'defalut-address':item.isDefault}"></a>
            </div>
            <div class="operation">
              <el-button type="primary" icon="edit" size="small"  @click="update(item)"></el-button>
              <el-button type="danger" icon="delete" size="small" :data-id="item.addressId" @click="del(item.addressId,i)"></el-button>
            </div>
          </div>
        </div>
        <div v-else>
          <div style="padding: 80px 0;text-align: center">
            <div style="font-size: 20px">你还未添加收货地址</div>
            <div style="margin: 20px ">
              <y-button text="添加地址" @btnClick="update()"></y-button>
            </div>
          </div>
        </div>
      </div>
    </y-shelf>
    <y-popup :open="popupOpen" @close='popupOpen=false' :title="popupTitle">
      <div slot="content" class="md" :data-id="msg.addressId">
        <div>
          <el-input type="text" placeholder="收货人姓名" v-model="msg.userName"></el-input>
        </div>
        <div>
          <el-input type="number" placeholder="手机号码" v-model="msg.tel"></el-input>
        </div>
<!--      数据源 https://github.com/wecatch/china_regions/tree/master/json    -->
<!--        TODO 地址模块的同学mysql创建字典表-->
<!--        <div>-->
<!--          <input type="text" placeholder="收货地址" v-model="msg.streetName">-->
<!--        </div>-->
        <div style="display: flex;flex-direction: row">
          <div style="flex: 1;">
            <el-select @change="_handleProvinceChange" v-model="provinceId" placeholder="请选择省份">
              <el-option
                v-for="(item, index) in provinceList"
                :key="index"
                :label="item.name"
                :value="item.id">
              </el-option>
            </el-select>
          </div>
          <div style="flex: 1;padding-left:4px">
            <el-select @change="_handleCityChange" v-model="cityId" placeholder="请选择市">
              <el-option
                v-for="(item, index) in cityList"
                :key="index"
                :label="item.name"
                :value="item.id">
              </el-option>
            </el-select>
          </div>
        </div>
        <div style="display: flex;">
          <el-select style="flex: 1" @change="_handleDistrictChange" v-model="districtId" placeholder="请选择区">
            <el-option
              v-for="(item, index) in districtList"
              :key="index"
              :label="item.name"
              :value="item.id">
            </el-option>
          </el-select>
        </div>
        <div>
          <el-input type="text" placeholder="详细地址" @change="_handleAddressChange" v-model="address"></el-input>
        </div>
        <div>
          <el-checkbox class="auto-login" v-model="msg._Default">设为默认</el-checkbox>
        </div>
        <y-button text='保存'
                  class="btn"
                  :classStyle="btnHighlight?'main-btn':'disabled-btn'"
                  @btnClick="save({userId:userId,addressId:msg.addressId,userName:msg.userName,tel:msg.tel,streetName:msg.streetName,_Default:msg._Default})">
        </y-button>
      </div>
    </y-popup>
  </div>
</template>
<script>
  import { addressList, addressUpdate, addressAdd, addressDel } from '/api/goods'
  import YButton from '/components/YButton'
  import YPopup from '/components/popup'
  import YShelf from '/components/shelf'

  import cityMap from '/utils/area/city'
  import provinceList from '/utils/area/province'
  import districtMap from '/utils/area/country'
  import Util from '/utils'

  export default {
    data () {
      return {
        addList: [],
        popupOpen: false,
        popupTitle: '管理收货地址',
        msg: {
          addressId: '',
          userName: '',
          tel: '',
          streetName: '',
          _Default: false
        },
        userId: '',

        // data
        provinceList: [],
        cityList: [],
        districtList: [],

        city: '', // 市
        province: '', // 省
        district: '', // 区
        cityId: null,
        provinceId: null,
        districtId: null,
        address: ''  // 街道
      }
    },
    computed: {
      btnHighlight () {
        let msg = this.msg
        return msg.userName && msg.tel && msg.streetName
      }
    },
    methods: {
      message (m) {
        this.$message.error({
          message: m
        })
      },
      _handleProvinceChange (provinceId) {
        if (!provinceId) {
          return
        }
        this.province = provinceList.find(p => p.id === provinceId).name
        this.cityId = null
        this.districtId = null
        this.districtList = []

        let cityList = cityMap[provinceId]
        this.cityList = cityList || []
        this.msg.streetName = this.province + '-' + this.city + '-' + this.district + '-' + this.address
      },
      _handleCityChange (cityId) {
        if (!cityId) {
          return
        }
        this.city = this.cityList.find(c => c.id === cityId).name
        this.districtId = null

        let districtList = districtMap[cityId]
        this.districtList = districtList || []
        this.msg.streetName = this.province + '-' + this.city + '-' + this.district + '-' + this.address
      },
      _handleDistrictChange (districtId) {
        if (!districtId) {
          return
        }
        this.district = this.districtList.find(d => d.id === districtId).name
        this.msg.streetName = this.province + '-' + this.city + '-' + this.district + '-' + this.address
      },
      _handleAddressChange () {
        this.msg.streetName = this.province + '-' + this.city + '-' + this.district + '-' + this.address
        // console.log('%c[addressList-_handleAddressChange]', 'color: #63ADD1', this.msg.streetName)
      },
      _addressList () {
        addressList().then(res => {
          let data = res.result
          if (data.length) {
            this.addList = res.result
            this.addressId = res.result[0].addressId || '1'
          } else {
            this.addList = []
          }
        })
      },
      _addressUpdate (params) {
        addressUpdate(params).then(res => {
          this._addressList()
        })
      },
      _addressAdd (params) {
        addressAdd(params).then(res => {
          if (res.success === true) {
            this._addressList()
          } else {
            this.message(res.message)
          }
        })
      },
      changeDef (item) {
        if (!item._Default) {
          item._Default = true
          this._addressUpdate(item)
        }
        return false
      },
      // 保存
      save (p) {
//        alert(p._Default)
        this.popupOpen = false
        if (p.addressId) {
          this._addressUpdate(p)
        } else {
          delete p.addressId
          this._addressAdd(p)
        }
      },
      // 删除
      del (addressId, i) {
        addressDel({addressId: addressId}).then(res => {
          if (res.success === true) {
            this.addList.splice(i, 1)
          } else {
            this.message('删除失败')
          }
        })
      },
      // 修改
      update (item) {
        if (item) {
          this.popupTitle = '管理收货地址'
          this.msg.userName = item.userName
          this.msg.tel = item.tel
          this.msg.streetName = item.streetName
          this.msg._Default = item._Default
          this.msg.addressId = item.addressId
          // init 地址选择框
          this._initAddressSelect(item.streetName)
          this.popupOpen = true
        }
      },
      addNewAddress () {
        this.popupTitle = '新增收货地址'
        this.msg.userName = ''
        this.msg.tel = ''
        this.msg.streetName = ''
        this.msg._Default = false
        this.msg.addressId = ''

        this._clearAddressSelect()
        this.popupOpen = true
      },
      _clearAddressSelect () {
        this.provinceId = null
        this.cityId = null
        this.districtId = null
      },
      _initAddressSelect (streetName) {
        let addressList = !Util.isEmpty(streetName) ? streetName.split('-') : []
        if (addressList.length >= 3) {
          this.province = addressList[0]
          this.city = addressList[1]
          this.district = addressList[2]
          this.address = addressList.length === 4 ? addressList[3] : ''

          // 查找对应省份
          let provinceObj = provinceList.find(p => p.name === this.province)
          if (!Util.isEmpty(provinceObj)) {
            this.provinceId = provinceObj.id
            this.cityList = cityMap[this.provinceId]
            // 查找对应城市
            let cityObj = cityMap[this.provinceId].find(c => c.name === this.city)
            if (!Util.isEmpty(cityObj)) {
              this.cityId = cityObj.id
              this.districtList = districtMap[this.cityId]
              // 查找对应地区
              let districtObj = districtMap[this.cityId].find(d => d.name === this.district)
              if (!Util.isEmpty(districtObj)) {
                this.districtId = districtObj.id
              }
            }
          }
        } else {
          // 老数据直接重置
          this.provinceId = null
          this.cityId = null
          this.districtId = null
        }
      }
    },
    filters: {
      addressFilter (streetName) {
        return streetName ? streetName.replace(new RegExp('-', 'g'), '') : ''
      }
    },
    created () {
      this.provinceList = provinceList
      this.cityList = []
      this.district = []
      this._addressList()
    },
    components: {
      YButton,
      YPopup,
      YShelf
    }
  }
</script>
<style scoped lang="scss">
  .table-title {
    position: relative;
    z-index: 1;
    line-height: 38px;
    height: 38px;
    padding: 0 0 0 38px;
    font-size: 12px;
    background: #eee;
    border-bottom: 1px solid #dbdbdb;
    border-bottom-color: rgba(0, 0, 0, .08);
    .name {
      float: left;
      text-align: left;
    }
    span {
      width: 137px;
      float: left;
      text-align: center;
      color: #838383;
    }
    .address {
      margin-left: 115px;
    }
    .tel {
      margin-left: 195px;
    }
  }

  .address-item {
    display: flex;
    align-items: center;
    height: 115px;
    text-align: center;
    .name {
      width: 106px;
    }
    .address-msg {
      flex: 1;
    }
    .telephone {
      width: 160px;
    }
    .defalut {
      width: 80px;
      > a {
        text-align: center;
        /*display: none;*/
      }
    }
    .operation {
      width: 135px;
      a {
        padding: 10px 5px;
      }
    }
    &:hover {
      .defalut > a {
        display: block;
      }
    }
  }

  .address-item + .address-item {
    border-top: 1px solid #CFCFCF;
  }

  .defalut-address {
    color: #626262;
    display: block;
    pointer-events: none;
    cursor: default;
  }

  .md {
    > div {
      text-align: left;
      margin-bottom: 15px;
    }
  }
  .btn {
    margin: 0;
    width: 100%;
    height: 44px;
    font-size: 14px;
    line-height: 44px
  }
</style>
<style lang="scss">
  .md .el-input__inner {
    width: 100%;
    height: 36px;
    font-size: 14px;
    padding: 10px 20px;
    border: 1px solid #ccc;
    border-radius: 6px;
    box-shadow: 0 2px 5px -4px rgba(0, 0, 0, .4) inset, -1px 0 3px -2px rgba(0, 0, 0, .1) inset;
    line-height: 36px;
  }
</style>
