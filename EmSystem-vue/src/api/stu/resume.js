import request from '@/utils/request'

// 上传简历信息
export function addResume(data) {
  return request({
    url: '/stu/upResume',
    method: 'post',
    data: data
  })
}

// 查询详细

export function listResume(data) {
  return request({
    url: '/stu/getResume/' + data,
    method: 'get'
  })
}

// 更新简历

export function updateResume(data) {
  return request({
    url: '/stu/updateResume',
    method: 'put',
    data: data
  })
}

// 获取简历json信息-MyJsonResume

export function getResume(data) {
  return request({
    url: '/stu/getMyJsonResume/' + data,
    method: 'get'
  })
}

// 批量生成图片

export function toImages(data) {
  return request({
    url: '/stu/download/resumeImages',
    method: 'post',
    data: data
  })
}

// 下载pdf

export function toPdf(data) {
  return request({
    url: '/stu/download/resumePdf/' + data,
    method: 'post'
  })
}
