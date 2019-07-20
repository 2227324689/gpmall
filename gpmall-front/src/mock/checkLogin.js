import {apis} from '../api/api'

const CheckLoginInfo =
  {
    'success': true,
    'message': 'success',
    'code': 200,
    'timestamp': 1563590236628,
    'result': {
      'id': null,
      'username': null,
      'phone': null,
      'email': null,
      'sex': null,
      'address': null,
      'file': null,
      'description': null,
      'points': null,
      'balance': null,
      'state': 0,
      'token': null,
      'message': '用户登录已过期'
    }
  }

export default {
  bootstrap (mock) {
    mock.onGet(apis.userInfo).reply(config => {
      return new Promise((resolve, reject) => {
        resolve([200, CheckLoginInfo])
      })
    })
  }
}
