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
      // url: '/api/blog/getPageList',
      url: process.env.WEB_API+'/blog/getPageList',
      method: 'post',
      data:params
    });
}

export function addBlog(params) {
  return request({
    url: '/api/blog/addBlog',
    method: 'post',
    data:params
  });
}

export function editBlog(params) {
  return request({
    url: '/api/blog/editBlog',
    method: 'post',
    data:params
  });
}

export function deleteBlog(params) {
  return request({
    url: '/api/blog/deleteBlog',
    method: 'post',
    data:params
  });
}

export function deleteBatchBlog(params) {
  return request({
    url: '/api/blog/deleteBatchBlog',
    method: 'post',
    data:params
  });
}








