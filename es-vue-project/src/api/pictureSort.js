import request from '@/utils/request'

/**
 * 博客内容相关
 */

export function getPictureSortList(params){
   return request({
     url: '/api/picSort/getPage',
     method:'post',
     data:params
   });
}

export function getPictureSortByUid(params){
  return request({
    url: '/api/picSort/getPicSortByUid',
    method:'get',
    params:params
  });
}

export function editPictureSort(params){
  return request({
    url: '/api/picSort/editPicSort',
    method:'post',
    data:params
  });
}

export function addPictureSort(params){
  return request({
    url: '/api/picSort/addPicSort',
    method:'post',
    data:params
  });
}

export function deletePictureSort(params){
  return request({
    url: '/api/picSort/delPicSort',
    method:'get',
    params:params
  });
}





