import {apis} from '../api/api'

const AllGoodsData = {
  'success': true,
  'message': 'success',
  'code': 200,
  'timestamp': 1563678036798,
  'result': {
    'total': 31,
    'data': [{
      'productId': 150642571432851,
      'salePrice': 2699.00,
      'productName': '优点智能 E1 推拉式智能指纹密码锁',
      'subTitle': '推拉一下，轻松开关',
      'productImageBig': 'https://resource.smartisan.com/resource/7ac3af5a92ad791c2b38bfe1e38ee334.jpg'
    }, {
      'productId': 150642571432850,
      'salePrice': 199.00,
      'productName': 'ACIL E1 颈挂式蓝牙耳机',
      'subTitle': '无感佩戴，一切变得简单',
      'productImageBig': 'https://resource.smartisan.com/resource/406eddef8808fa5a9be9594d07ef8643.jpg'
    }, {
      'productId': 150642571432849,
      'salePrice': 9.90,
      'productName': 'Smartisan 明信片',
      'subTitle': '优质卡纸、包装精致、色彩饱满',
      'productImageBig': 'https://resource.smartisan.com/resource/3973d009d182d8023bea6250b9a3959e.jpg'
    }, {
      'productId': 150642571432848,
      'salePrice': 199.00,
      'productName': 'Smartisan 牛津纺衬衫',
      'subTitle': '一件无拘无束的舒适衬衫',
      'productImageBig': 'https://resource.smartisan.com/resource/a1c53b5f12dd7ef790cadec0eaeaf466.jpg'
    }, {
      'productId': 150642571432847,
      'salePrice': 249.00,
      'productName': 'Smartisan Polo衫 经典款',
      'subTitle': '一件表里如一的舒适 POLO 衫',
      'productImageBig': 'https://resource.smartisan.com/resource/daa975651d6d700c0f886718c520ee19.jpg'
    }, {
      'productId': 150642571432846,
      'salePrice': 149.00,
      'productName': 'Smartisan T恤 任天堂发售“红白机”',
      'subTitle': '100% 美国 SUPIMA 棉、舒适拉绒质地',
      'productImageBig': 'https://resource.smartisan.com/resource/804edf579887b3e1fae4e20a379be5b5.png'
    }, {
      'productId': 150642571432845,
      'salePrice': 199.00,
      'productName': 'Smartisan 帆布鞋',
      'subTitle': '一双踏实、舒适的帆布鞋',
      'productImageBig': 'https://resource.smartisan.com/resource/2f9a0f5f3dedf0ed813622003f1b287b.jpg'
    }, {
      'productId': 150642571432844,
      'salePrice': 2999.00,
      'productName': '畅呼吸智能空气净化器超级除甲醛版',
      'subTitle': '购新空净 赠价值 699 元活性炭滤芯',
      'productImageBig': 'https://resource.smartisan.com/resource/71432ad30288fb860a4389881069b874.png'
    }, {
      'productId': 150642571432843,
      'salePrice': 1999.00,
      'productName': '坚果 3',
      'subTitle': '漂亮得不像实力派',
      'productImageBig': 'https://resource.smartisan.com/resource/718bcecced0df1cd23bbdb9cc1f70b7d.png'
    }, {
      'productId': 150642571432842,
      'salePrice': 79.00,
      'productName': '坚果 3 "足迹"背贴 乐高创始人出生',
      'subTitle': '1891 年 4 月 7 日',
      'productImageBig': 'https://resource.smartisan.com/resource/abb6986430536cd9365352b434f3c568.jpg'
    }, {
      'productId': 150642571432841,
      'salePrice': 49.00,
      'productName': '坚果 3 TPU 软胶保护套',
      'subTitle': 'TPU 环保材质、完美贴合、周到防护',
      'productImageBig': 'https://resource.smartisan.com/resource/b899d9b82c4bc2710492a26af021d484.jpg'
    }, {
      'productId': 150642571432840,
      'salePrice': 89.00,
      'productName': 'Smartisan 半入耳式耳机',
      'subTitle': '经典配色、专业调音、高品质麦克风',
      'productImageBig': 'https://resource.smartisan.com/resource/9c1d958f10a811df841298d50e1ca7c0.jpg'
    }, {
      'productId': 150642571432839,
      'salePrice': 29.00,
      'productName': '坚果 3 TPU 软胶透明保护套',
      'subTitle': '轻薄透明、完美贴合、TPU 环保材质',
      'productImageBig': 'https://resource.smartisan.com/resource/5e4b1feddb13639550849f12f6b2e202.jpg'
    }, {
      'productId': 150642571432838,
      'salePrice': 79.00,
      'productName': '坚果 3 绒布国旗保护套',
      'subTitle': '质感精良、完美贴合、周到防护',
      'productImageBig': 'https://resource.smartisan.com/resource/63ea40e5c64db1c6b1d480c48fe01272.jpg'
    }, {
      'productId': 150642571432837,
      'salePrice': 49.00,
      'productName': '坚果 3 前屏钢化玻璃保护膜',
      'subTitle': '超强透光率、耐刮花、防指纹',
      'productImageBig': 'https://resource.smartisan.com/resource/3a7522290397a9444d7355298248f197.jpg'
    }, {
      'productId': 150642571432836,
      'salePrice': 149.00,
      'productName': 'Smartisan T恤 伍迪·艾伦出生',
      'subTitle': '一件内外兼修的舒适T恤',
      'productImageBig': 'https://resource.smartisan.com/resource/f96f0879768bc317b74e7cf9e3d96884.jpg'
    }, {
      'productId': 816448,
      'salePrice': 2799.00,
      'productName': '极米无屏电视 CC',
      'subTitle': '720P 高清分辨率、JBL 音响、两万毫安续航力',
      'productImageBig': 'http://image.smartisanos.cn/resource/41cb562a47d4831e199ed7e2057f3b61.jpg'
    }, {
      'productId': 738388,
      'salePrice': 39.00,
      'productName': 'Smartisan 原装 Type-C 数据线',
      'subTitle': 'PTC 过温保护、凹形设计、TPE 环保材质',
      'productImageBig': 'http://image.smartisanos.cn/resource/c79a73ffc6f8e782160d978f49f543dc.jpg'
    }, {
      'productId': 691300,
      'salePrice': 199.00,
      'productName': 'Smartisan 快充移动电源 10000mAh',
      'subTitle': '10000mAh 双向快充、轻盈便携、高标准安全保护',
      'productImageBig': 'http://image.smartisanos.cn/resource/0540778097a009364f2dcbb8c5286451.jpg'
    }, {
      'productId': 679532,
      'salePrice': 59.00,
      'productName': 'Smartisan 原装快充充电器 18W',
      'subTitle': '18W 安全快充、支持主流 QC3.0, MTK PE+2.0 快充协议',
      'productImageBig': 'http://image.smartisanos.cn/resource/dc53bd870ee64d2053ecc51750ece43a.jpg'
    }]
  }
}

export default {
  bootstrap (mock) {
    mock.onGet(apis.getAllGoods).reply(config => {
      return new Promise((resolve, reject) => {
        resolve([200, AllGoodsData])
      })
    })
  }
}
