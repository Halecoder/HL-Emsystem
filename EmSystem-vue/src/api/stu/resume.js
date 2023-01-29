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
