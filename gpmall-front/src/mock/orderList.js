import {apis} from '../api/api'

const OrderListData = {
  'success': true,
  'message': 'success',
  'code': 200,
  'timestamp': 1563679565361,
  'result': {
    'total': 213,
    'data': [{
      'orderId': 156367946889065,
      'orderTotal': 9.90,
      'addressInfo': {
        'addressId': null,
        'userId': null,
        'userName': 'JUNMINMG REN',
        'tel': '17610178750',
        'streetName': '三生三世',
        'isDefault': null
      },
      'goodsList': [{
        'productId': 150642571432849,
        'salePrice': 9.90,
        'productNum': 1,
        'limitNum': null,
        'checked': null,
        'productName': 'Smartisan 明信片',
        'productImg': 'https://resource.smartisan.com/resource/3973d009d182d8023bea6250b9a3959e.jpg'
      }],
      'orderStatus': '0',
      'createDate': '2019-07-21 11:24',
      'closeDate': null,
      'finishDate': null,
      'payDate': null
    }, {
      'orderId': 156365722393872,
      'orderTotal': 400.00,
      'addressInfo': {
        'addressId': null,
        'userId': null,
        'userName': 'JUNMINMG REN',
        'tel': '17610178750',
        'streetName': '三生三世',
        'isDefault': null
      },
      'goodsList': [{
        'productId': 150642571432850,
        'salePrice': 199.00,
        'productNum': 2,
        'limitNum': null,
        'checked': null,
        'productName': 'ACIL E1 颈挂式蓝牙耳机',
        'productImg': 'https://resource.smartisan.com/resource/406eddef8808fa5a9be9594d07ef8643.jpg'
      }, {
        'productId': 150635087972564,
        'salePrice': 1.00,
        'productNum': 2,
        'limitNum': null,
        'checked': null,
        'productName': '支付测试商品 IPhone X 全面屏 全面绽放',
        'productImg': 'https://i.loli.net/2018/07/13/5b48ac7766d98.png'
      }],
      'orderStatus': '0',
      'createDate': '2019-07-21 05:13',
      'closeDate': null,
      'finishDate': null,
      'payDate': null
    }, {
      'orderId': 156364278780903,
      'orderTotal': 1999.00,
      'addressInfo': {
        'addressId': null,
        'userId': null,
        'userName': 'JUNMINMG REN',
        'tel': '17610178750',
        'streetName': '三生三世',
        'isDefault': null
      },
      'goodsList': [{
        'productId': 150642571432843,
        'salePrice': 1999.00,
        'productNum': 1,
        'limitNum': null,
        'checked': null,
        'productName': '坚果 3',
        'productImg': 'https://resource.smartisan.com/resource/718bcecced0df1cd23bbdb9cc1f70b7d.png'
      }],
      'orderStatus': '0',
      'createDate': '2019-07-21 01:13',
      'closeDate': null,
      'finishDate': null,
      'payDate': null
    }, {
      'orderId': 156363217890645,
      'orderTotal': 9.90,
      'addressInfo': {
        'addressId': null,
        'userId': null,
        'userName': 'JUNMINMG REN',
        'tel': '17610178750',
        'streetName': '三生三世',
        'isDefault': null
      },
      'goodsList': [{
        'productId': 150642571432849,
        'salePrice': 9.90,
        'productNum': 1,
        'limitNum': null,
        'checked': null,
        'productName': 'Smartisan 明信片',
        'productImg': 'https://resource.smartisan.com/resource/3973d009d182d8023bea6250b9a3959e.jpg'
      }],
      'orderStatus': '0',
      'createDate': '2019-07-20 22:16',
      'closeDate': null,
      'finishDate': null,
      'payDate': null
    }, {
      'orderId': 156362278018849,
      'orderTotal': 1.00,
      'addressInfo': {
        'addressId': null,
        'userId': null,
        'userName': 'JUNMINMG REN',
        'tel': '17610178750',
        'streetName': '三生三世',
        'isDefault': null
      },
      'goodsList': [{
        'productId': 150635087972564,
        'salePrice': 1.00,
        'productNum': 1,
        'limitNum': null,
        'checked': null,
        'productName': '支付测试商品 IPhone X 全面屏 全面绽放',
        'productImg': 'https://i.loli.net/2018/07/13/5b48ac7766d98.png'
      }],
      'orderStatus': '0',
      'createDate': '2019-07-20 19:39',
      'closeDate': null,
      'finishDate': null,
      'payDate': null
    }]
  }
}

export default {
  bootstrap (mock) {
    mock.onGet(apis.orderList).reply(config => {
      return new Promise((resolve, reject) => {
        resolve([200, OrderListData])
      })
    })
  }
}
