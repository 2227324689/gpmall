import axios from 'axios'
import MockAdapter from 'axios-mock-adapter'
import CheckLogin from './checkLogin.js'
import Home from './home.js'
import NavList from './navList.js'

export default {
  /**
   * mock bootstrap
   */
  bootstrap () {
    let mock = new MockAdapter(axios)
    CheckLogin.bootstrap(mock)
    Home.bootstrap(mock)
    NavList.bootstrap(mock)
  }
}
