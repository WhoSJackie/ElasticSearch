import axios from 'axios'
import router from '@/router/index'
// import {getCookie} from "@/utils/cookieUtils";
// 创建axios实例
const service = axios.create({
  baseURL: '', // api 的 base_url
  timeout: 60000 // 请求超时时间 60秒
})


export default service
