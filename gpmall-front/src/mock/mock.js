import axios from 'axios'
import MockAdapter from 'axios-mock-adapter'
import CheckLogin from './checkLogin'
import Home from './home'
import NavList from './navList'
import ProductDetail from './productDetail'
import AddCard from './addCart'
import Login from './login'
import CartList from './cartList'
import QuickSearch from './quickSearch'
import AllGoods from './allGoods'
import LogOut from './loginOut'
import OrderList from './orderList'
import AddressList from './addressList'

export default {
  /**
   * mock bootstrap
   */
  bootstrap () {
    let mock = new MockAdapter(axios)
    CheckLogin.bootstrap(mock)
    Home.bootstrap(mock)
    NavList.bootstrap(mock)
    ProductDetail.bootstrap(mock)
    AddCard.bootstrap(mock)
    Login.bootstrap(mock)
    CartList.bootstrap(mock)
    QuickSearch.bootstrap(mock)
    AllGoods.bootstrap(mock)
    LogOut.bootstrap(mock)
    OrderList.bootstrap(mock)
    AddressList.bootstrap(mock)
  }
}
