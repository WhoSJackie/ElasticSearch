import request from '@/utils/request'

/**
 * 博客内容相关
 */

export function getBlogByLevel(params){
   return request({
     url: '/api/blogContent/getBlogByLevel',
     method:'post',
     data:params
   });
}

export function getBlogByUid(params) {
  return request({
    url: '/api/blogContent/getBlogByUid',
    method: 'post',
    data:params
  });
}

  export function getBlogList(params) {
    return request({
      url: '/api/blog/getPageList',
      method: 'post',
      data:params
    })
  }




