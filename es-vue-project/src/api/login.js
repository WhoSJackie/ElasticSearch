import request from '@/utils/request'

/**
 * 第三方登录
 * @param params
 */
export function login(params) {
  return request({
    url: process.env.WEB_API + '/auth/render',
    method: 'post',
    data:params
  })
}

export function authVerify(params) {
  return request({
    url: process.env.WEB_API + '/auth/verify/',
    method: 'get',
    params
  })
}

export function getMenu(params) {
    return request({
      url: '/api/auth/getMenu/',
      method: 'get',
      params
    })
  }

