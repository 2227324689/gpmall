import axios from 'axios'

axios.interceptors.response.use((res) => {
  if (res.status === 200) {
    // 如果当前是mock则打印接口返回值以方便调试
    console.groupCollapsed(`%cmock模式::[${res.config.method}]${res.config.url}: %c点击查看`, 'color: green', 'color: blue')
    console.log(`data参数：`, res.config.data ? JSON.parse(res.config.data) : '未传递')
    console.log(`params参数：`, res.config.params ? res.config.params : '未传递')
    console.log(`接口返回数据：`, res.data)
    console.groupEnd()
    return Promise.resolve(res)
  }
  return res
}, err => {
  return Promise.reject(err)
})
