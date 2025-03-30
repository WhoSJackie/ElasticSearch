import request from '@/utils/request'

/**
 * 博客分类相关
 */

export function getBlogSortList(params) {
  return request({
    url: '/api/blogSort/getPageList',
    method: 'post',
    data:params
  })
}




