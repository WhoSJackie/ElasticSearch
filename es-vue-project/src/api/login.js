import request from '@/utils/request'

export function login(params) {
  return request({
    url: '/api/auth/login',
    method: 'get',
    params: params
  })
}

export function getMenu(params) {
    return request({
      url: '/api/auth/getMenu/',
      method: 'get',
      params: params
    })
}

export function getInfo(params){
  return request({
    url: '/api/auth/getUserInfo',
    method: 'get',
    params: params
  })
}

export function validCode(params){
  return request({
    url: '/api/auth/validCode',
    method: 'get',
    params: params
  })
}

export function logout(params) {
  return request({
    url: '/api/auth/logout',
    method: 'get',
    params: params
  })
}









