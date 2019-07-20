import {apis} from '../api/api'

const navList = {
  'success': true,
  'message': 'success',
  'code': 200,
  'timestamp': 1563590237008,
  'result': [{
    'id': 55,
    'panelId': 0,
    'type': 1,
    'productId': null,
    'sortOrder': 1,
    'fullUrl': 'http://xmall.exrick.cn/#/goods?cid=1184',
    'picUrl': '品牌周边',
    'picUrl2': null,
    'picUrl3': null,
    'created': 1532695701000,
    'updated': 1532701971000,
    'salePrice': null,
    'productName': null,
    'subTitle': null,
    'productImageBig': '品牌周边'
  }, {
    'id': 58,
    'panelId': 0,
    'type': 1,
    'productId': null,
    'sortOrder': 2,
    'fullUrl': 'http://xmall.exrick.cn/#/thanks',
    'picUrl': '捐赠名单',
    'picUrl2': null,
    'picUrl3': null,
    'created': 1532695807000,
    'updated': 1532701518000,
    'salePrice': null,
    'productName': null,
    'subTitle': null,
    'productImageBig': '捐赠名单'
  }, {
    'id': 59,
    'panelId': 0,
    'type': 0,
    'productId': null,
    'sortOrder': 3,
    'fullUrl': 'http://xmadmin.exrick.cn',
    'picUrl': '后台管理系统',
    'picUrl2': null,
    'picUrl3': null,
    'created': 1532701544000,
    'updated': 1532701614000,
    'salePrice': null,
    'productName': null,
    'subTitle': null,
    'productImageBig': '后台管理系统'
  }, {
    'id': 60,
    'panelId': 0,
    'type': 0,
    'productId': null,
    'sortOrder': 4,
    'fullUrl': 'http://xpay.exrick.cn',
    'picUrl': 'XPay支付系统',
    'picUrl2': null,
    'picUrl3': null,
    'created': 1532701563000,
    'updated': 1532701610000,
    'salePrice': null,
    'productName': null,
    'subTitle': null,
    'productImageBig': 'XPay支付系统'
  }, {
    'id': 61,
    'panelId': 0,
    'type': 0,
    'productId': null,
    'sortOrder': 5,
    'fullUrl': 'https://github.com/Exrick/x-boot',
    'picUrl': 'XBoot开发平台',
    'picUrl2': null,
    'picUrl3': null,
    'created': 1532701581000,
    'updated': 1541324408000,
    'salePrice': null,
    'productName': null,
    'subTitle': null,
    'productImageBig': 'XBoot开发平台'
  }, {
    'id': 62,
    'panelId': 0,
    'type': 0,
    'productId': null,
    'sortOrder': 6,
    'fullUrl': 'https://www.bilibili.com/video/av23121122/',
    'picUrl': '宣传视频',
    'picUrl2': null,
    'picUrl3': null,
    'created': 1532701604000,
    'updated': 1532701604000,
    'salePrice': null,
    'productName': null,
    'subTitle': null,
    'productImageBig': '宣传视频'
  }, {
    'id': 63,
    'panelId': 0,
    'type': 0,
    'productId': null,
    'sortOrder': 7,
    'fullUrl': 'https://github.com/Exrick/xmall',
    'picUrl': 'Github',
    'picUrl2': null,
    'picUrl3': null,
    'created': 1532701642000,
    'updated': 1532701646000,
    'salePrice': null,
    'productName': null,
    'subTitle': null,
    'productImageBig': 'Github'
  }, {
    'id': 64,
    'panelId': 0,
    'type': 0,
    'productId': null,
    'sortOrder': 8,
    'fullUrl': 'http://wpa.qq.com/msgrd?v=3&uin=1012139570&site=qq&menu=yes',
    'picUrl': '商用授权',
    'picUrl2': null,
    'picUrl3': null,
    'created': 1541324383000,
    'updated': 1541324383000,
    'salePrice': null,
    'productName': null,
    'subTitle': null,
    'productImageBig': '商用授权'
  }]
}

export default {
  bootstrap (mock) {
    mock.onGet(apis.navList).reply(config => {
      return new Promise((resolve, reject) => {
        resolve([200, navList])
      })
    })
  }
}
