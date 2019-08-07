import http from './public'
import {apis} from './api'

// 登陆
export const userLogin = (params) => {
  return http.fetchPost(apis.userLogin, params)
}
// 退出登陆
export const loginOut = (params) => {
  return http.fetchGet(apis.loginOut, params)
}
// 用户信息
export const userInfo = (params) => {
  return http.fetchGet(apis.userInfo, params)
}
// 注册账号
export const register = (params) => {
  return http.fetchPost(apis.register, params)
}
// 上传图片
export const upload = (params) => {
  return http.fetchPost(apis.upload, params)
}
// 修改头像
export const updateheadimage = (params) => {
  return http.fetchPost(apis.updateheadimage, params)
}
/* // 捐赠列表
export const thanksList = (params) => {
  return http.fetchGet('/member/thanks', params)
} */
// 首页接口
export const productHome = (params) => {
  return http.fetchGet(apis.productHome, params)
}
// 首页接口
export const navList = (params) => {
  return http.fetchGet(apis.navList, params)
}
// 推荐板块
export const recommend = (params) => {
  return http.fetchGet(apis.recommend, params)
}

/* // 捐赠板块
export const thank = (params) => {
  return http.fetchGet('/goods/thank', params)
} */

export const initKaptcha = (params) => {
  return http.fetchGet('/user/kaptcha?t=' + (new Date()).getTime(), params)
}

