import {apis} from '../api/api'

const CheckLoginInfo = {
  'success': true,
  'message': 'success',
  'code': 200,
  'timestamp': 1563677894503,
  'result': {
    'id': 62,
    'username': 'test',
    'phone': null,
    'email': null,
    'sex': null,
    'address': null,
    'file': 'https://istio.tech/images/avatar.png',
    'description': null,
    'points': null,
    'balance': null,
    'state': 1,
    'token': 'd476156f-7e6c-4296-8ce8-c19bea6626ba',
    'message': null
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
