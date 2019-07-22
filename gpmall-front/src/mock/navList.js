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
    'fullUrl': '#',
    'picUrl': '手机',
    'picUrl2': null,
    'picUrl3': null,
    'created': 1532695701000,
    'updated': 1532701971000,
    'salePrice': null,
    'productName': null,
    'subTitle': null,
    'productImageBig': '手机'
  }, {
    'id': 58,
    'panelId': 0,
    'type': 1,
    'productId': null,
    'sortOrder': 2,
    'fullUrl': '#',
    'picUrl': '官方配件',
    'picUrl2': null,
    'picUrl3': null,
    'created': 1532695807000,
    'updated': 1532701518000,
    'salePrice': null,
    'productName': null,
    'subTitle': null,
    'productImageBig': '官方配件'
  }, {
    'id': 59,
    'panelId': 0,
    'type': 0,
    'productId': null,
    'sortOrder': 3,
    'fullUrl': '#',
    'picUrl': '服饰箱包',
    'picUrl2': null,
    'picUrl3': null,
    'created': 1532701544000,
    'updated': 1532701614000,
    'salePrice': null,
    'productName': null,
    'subTitle': null,
    'productImageBig': '服饰箱包'
  }, {
    'id': 60,
    'panelId': 0,
    'type': 0,
    'productId': null,
    'sortOrder': 4,
    'fullUrl': '#',
    'picUrl': '畅呼吸',
    'picUrl2': null,
    'picUrl3': null,
    'created': 1532701563000,
    'updated': 1532701610000,
    'salePrice': null,
    'productName': null,
    'subTitle': null,
    'productImageBig': '畅呼吸'
  }, {
    'id': 61,
    'panelId': 0,
    'type': 0,
    'productId': null,
    'sortOrder': 5,
    'fullUrl': '#',
    'picUrl': '服务',
    'picUrl2': null,
    'picUrl3': null,
    'created': 1532701581000,
    'updated': 1541324408000,
    'salePrice': null,
    'productName': null,
    'subTitle': null,
    'productImageBig': '服务'
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
