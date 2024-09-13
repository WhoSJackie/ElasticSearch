import commonUtil from './commonUtils'

export default {
  install(Vue, options) {
    Vue.prototype.$ECode = commonUtil.ECode
  }
}
