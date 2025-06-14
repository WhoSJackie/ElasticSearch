import request from '@/utils/request'

/**
 * 博客分类相关
 */

 export function getTagList(params) {
  return request({
    url: '/api/tag/getPageList',
    method: 'post',
    data: params
  })
}

  export function addTag(params) {
    return request({
      url: '/api/tag/addBlogTag',
      method: 'post',
      data: params
    })
  }

  export function editTag(params) {
    return request({
      url: '/api/tag/editBlogTag',
      method: 'post',
      data: params
    })
  }

  export function deleteBatchTag(params) {
    return request({
      url: '/api/tag/deleteBatch',
      method: 'post',
      data: params
    })
  }






