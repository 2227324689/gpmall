import {apis} from '../api/api'

const LogOutData = {
  'success': true,
  'message': 'success',
  'code': 200,
  'timestamp': 1563678797287,
  'result': null
}

export default {
  bootstrap (mock) {
    mock.onGet(apis.loginOut).reply(config => {
      return new Promise((resolve, reject) => {
        resolve([200, LogOutData])
      })
    })
  }
}

