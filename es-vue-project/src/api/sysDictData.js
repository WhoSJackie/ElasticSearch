import request from '@/utils/request'

/**
 * 博客内容相关
 */

export function getListByDictType(params){
   return request({
     url: '/api/dict/getSingle',
     method:'get',
     params
   });
}

export function getListByDictTypeList(params) {
  return request({
    url: '/api/dict/getMultiple',
    method: 'post',
    data:params
  });
}





