import {apis} from '../api/api'

const AddressListData = {
  'success': true,
  'message': 'success',
  'code': 200,
  'timestamp': 1563679745576,
  'result': [{
    'addressId': 863,
    'userId': 62,
    'userName': 'Mic',
    'tel': '18073804421',
    'streetName': '长沙岳麓区',
    'isDefault': false
  }]
}

export default {
  bootstrap (mock) {
    mock.onPost(apis.addressList).reply(config => {
      return new Promise((resolve, reject) => {
        resolve([200, AddressListData])
      })
    })
  }
}
