import {apis} from '../api/api'

const searchData = {
  'success': true,
  'message': 'success',
  'code': 200,
  'timestamp': 1563602545414,
  'result': {
    'itemList': [{
      'productId': 150642571432835,
      'sortOrder': 2,
      'fullUrl': '',
      'salePrice': 1.00,
      'productName': '捐赠商品',
      'subTitle': '您的捐赠将用于本站维护 给您带来更好的体验',
      'productImageBig': 'https://i.loli.net/2018/11/04/5bdeba4028e90.png',
      'categoryName': '手机'
    }],
    'recordCount': 10,
    'totalPages': 2
  },
  'hits': {
    'hits': [{
      '_source': {
        'productName': 'RT手机'
      }
    }, {
      '_source': {
        'productName': '锤子耳机'
      }
    }]
  }
}

export default {
  bootstrap (mock) {
    mock.onGet(apis.getQuickSearch).reply(config => {
      return new Promise((resolve, reject) => {
        resolve([200, searchData])
      })
    })
  }
}
