import {apis} from '../api/api'
const productDetail = {
  'success': true,
  'message': 'success',
  'code': 200,
  'timestamp': 1563602545414,
  'result': {
    'productId': 150635087972564,
    'salePrice': 1.00,
    'productName': '支付测试商品 IPhone X 全面屏 全面绽放',
    'subTitle': '此仅为支付测试商品 拍下不会发货',
    'limitNum': 100,
    'productImageBig': 'https://i.loli.net/2018/07/13/5b48ac7766d98.png',
    'detail': '<p style="text-align:center;">\n\t<img src="https://img30.360buyimg.com/popWaterMark/jfs/t7843/137/3005340945/124833/dc7c71f2/59b8ccd1N2bffd055.jpg" alt="" /><img src="https://img30.360buyimg.com/popWaterMark/jfs/t8764/314/1380452846/296346/d62490e2/59b8ccd1N96ce760d.jpg" alt="" /><img src="https://img30.360buyimg.com/popWaterMark/jfs/t8710/275/1373463301/363710/ebf00bff/59b8ccbaN2d563f74.jpg" alt="" /><img src="https://img30.360buyimg.com/popWaterMark/jfs/t8632/330/1390725687/229853/e56f9e1b/59b8ccd1N7b8b6bdb.jpg" alt="" /><img src="https://img30.360buyimg.com/popWaterMark/jfs/t9115/290/1376678976/488369/591760dc/59b8ccc6N1563a61b.jpg" alt="" /><img src="https://img30.360buyimg.com/popWaterMark/jfs/t8233/331/1431263348/183032/b875528c/59b8ccd1Ne7e633e3.jpg" alt="" /><img src="https://img30.360buyimg.com/popWaterMark/jfs/t8785/253/890847377/186916/c467a464/59b8ccd1N4551397c.jpg" alt="" /> <img src="https://img30.360buyimg.com/popWaterMark/jfs/t8728/276/1416802585/172158/1516ec08/59b8ccd1N95aae9c9.jpg" alt="" /> <img src="https://img30.360buyimg.com/popWaterMark/jfs/t9082/133/1223014275/307097/58f97021/59b8ccd2Nebfc633a.jpg" alt="" /><img src="https://img30.360buyimg.com/popWaterMark/jfs/t9052/275/1400615286/155643/1b0ecf44/59b8ccd2N46bd82bf.jpg" alt="" /> <img src="https://img30.360buyimg.com/popWaterMark/jfs/t9169/240/1361662217/193435/24ed9b93/59b8ccd4N03cec407.jpg" alt="" /> <img src="https://img30.360buyimg.com/popWaterMark/jfs/t7390/232/3008585906/285016/56cbb12/59b8ccd4Nc8434af8.jpg" alt="" /> \n</p>',
    'productImageSmall': ['https://i.loli.net/2018/07/13/5b48ac7766d98.png', 'https://i.loli.net/2018/07/13/5b48ac9135c5f.png', 'https://i.loli.net/2018/07/13/5b48ac9c2be6c.png', 'https://i.loli.net/2018/07/13/5b48aca99c8b6.png', 'https://i.loli.net/2018/07/13/5b48a7f468bf2.png']
  }
}

export default {
  bootstrap (mock) {
    mock.onGet(apis.productDet).reply(config => {
      return new Promise((resolve, reject) => {
        resolve([200, productDetail])
      })
    })
  }
}
