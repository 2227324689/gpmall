import http from './public'
import {apis} from './api'

// 商品列表
export const getAllGoods = (params) => {
  return http.fetchGet(apis.getAllGoods, params)
}
// 获取购物车列表
export const getCartList = (params) => {
  return http.fetchGet(apis.getCartList, params)
}
// 加入购物车
export const addCart = (params) => {
  return http.fetchPost(apis.addCart, params)
}
// 删除购物车
export const delCart = (params) => {
  return http.fetchPost(apis.delCart, params)
}
// 删除购物车勾选商品
export const delCartChecked = (params) => {
  return http.fetchPost(apis.delCartChecked, params)
}
// 编辑购物车
export const cartEdit = (params) => {
  return http.fetchPost(apis.cartEdit, params)
}
// 全选
export const editCheckAll = (params) => {
  return http.fetchPost(apis.editCheckAll, params)
}
// 删除整条购物车
export const cartDel = (params) => {
  return http.fetchPost(apis.cartDel, params)
}
// 获取用户地址
export const addressList = (params) => {
  return http.fetchPost(apis.addressList, params)
}
// 通过id获取地址
export const getAddress = (params) => {
  return http.fetchPost(apis.getAddress, params)
}
// 修改收货地址
export const addressUpdate = (params) => {
  return http.fetchPost(apis.addressUpdate, params)
}
// 添加收货地址
export const addressAdd = (params) => {
  return http.fetchPost(apis.addressAdd, params)
}

// 删除收货地址
export const addressDel = (params) => {
  return http.fetchPost(apis.addressDel, params)
}
// 生成订单
export const submitOrder = (params) => {
  return http.fetchPost(apis.submitOrder, params)
}
// 支付
export const payMent = (params) => {
  return http.fetchPost(apis.payMent, params)
}
// 获取用户订单
export const orderList = (params) => {
  return http.fetchGet(apis.orderList, params)
}
// 获取单个订单详情
export const getOrderDet = (params) => {
  return http.fetchGet(apis.getOrderDet, params)
}
// 取消订单
export const cancelOrder = (params) => {
  return http.fetchPost(apis.cancelOrder, params)
}
// 商品详情
export const productDet = (params) => {
  return http.fetchGet(apis.productDet, params)
}
// 删除订单
export const delOrder = (params) => {
  return http.fetchGet(apis.delOrder, params)
}
// 商品列表
export const getSearch = (params) => {
  return http.fetchGet(apis.getSearch, params)
}
// 快速搜索
export const getQuickSearch = (params) => {
  return http.fetchGet(apis.getQuickSearch, params)
}
