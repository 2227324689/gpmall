export default {
  isEmpty (obj) {
    if (!obj) {
      return true
    }
    if (obj instanceof Array) {
      return !obj || obj.length === 0
    }
    if (typeof obj === 'string') {
      return obj.trim() === ''
    }
  }
}
