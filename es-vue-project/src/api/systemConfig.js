import request from '@/utils/request'

/**
 * 第三方登录
 * @param params
 */
export function getSystemConfig(params) {
  return request({
    // url: process.env.WEB_API + '/oauth/render',
    url: '/api/auth/getSystemConfig',
    method: 'get',
    params:params
  })
}

