import {apis} from '../api/api'

const LoginData = {
  'success': true,
  'message': 'success',
  'code': 200,
  'timestamp': 1563605326678,
  'result': {
    'id': 62,
    'username': 'test',
    'phone': null,
    'email': null,
    'sex': null,
    'address': null,
    'file': 'http://p77xsahe9.bkt.clouddn.com/1563551994449.png',
    'description': null,
    'points': null,
    'balance': null,
    'state': 1,
    'token': '85afe627-ddf9-4eb3-953b-051361909e6a',
    'message': null
  }
}

export default {
  bootstrap (mock) {
    mock.onGet(apis.userLogin).reply(config => {
      return new Promise((resolve, reject) => {
        resolve([200, LoginData])
      })
    })
  }
}
