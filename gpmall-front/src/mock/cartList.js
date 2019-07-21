import {apis} from '../api/api'

const CartListData = {
  'success': true,
  'message': 'success',
  'code': 200,
  'timestamp': 1563605549921,
  'result': [{
    'productId': 150635087972564,
    'salePrice': 1.00,
    'productNum': 1,
    'limitNum': 100,
    'checked': '1',
    'productName': '支付测试商品 IPhone X 全面屏 全面绽放',
    'productImg': 'https://i.loli.net/2018/07/13/5b48ac7766d98.png'
  }, {
    'productId': 150642571432850,
    'salePrice': 199.00,
    'productNum': 1,
    'limitNum': 100,
    'checked': '1',
    'productName': 'ACIL E1 颈挂式蓝牙耳机',
    'productImg': 'https://resource.smartisan.com/resource/406eddef8808fa5a9be9594d07ef8643.jpg'
  }]
}

export default {
  bootstrap (mock) {
    mock.onGet(apis.getCartList).reply(config => {
      return new Promise((resolve, reject) => {
        resolve([200, CartListData])
      })
    })
  }
}
