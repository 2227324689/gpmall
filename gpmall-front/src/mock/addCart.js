import {apis} from '../api/api'

const AddCartData = {
  'success': true,
  'message': 'success',
  'code': 200,
  'timestamp': 1563605326848,
  'result': 1
}

export default {
  bootstrap (mock) {
    mock.onGet(apis.addCart).reply(config => {
      return new Promise((resolve, reject) => {
        resolve([200, AddCartData])
      })
    })
  }
}
