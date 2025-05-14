import request from '@/utils/request'

/**
 * 博客内容相关
 */

export function getPictureList(params){
   return request({
     url: '/api/pic/getPage',
     method:'post',
     data:params
   });
}

export function addPictures(params){
  return request({
    url: '/api/pic/addPictures',
    method:'post',
    data:params
  });
}

export function deletePictures(params){
  return request({
    url: '/api/pic/deletePictures',
    method:'get',
    params:{
      delPic:params
    }
  });
}




