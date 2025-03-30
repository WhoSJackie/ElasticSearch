import request from '@/utils/request'

/**
 * 博客分类相关
 */

export function getTagList(params) {
  return request({
    url: '/api/tag/getPageList',
    method: 'post',
    data:params
  })
}




