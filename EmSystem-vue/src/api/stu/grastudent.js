import request from '@/utils/request'
import { parseStrEmpty } from '@/utils/ruoyi'

// 添加个人信息
export function addStuInfo(data) {
  return request({
    url: '/stu/upStuInfo',
    method: 'post',
    data: data
  })
}
// 查询信息
export function listStuInfo(data) {
  return request({
    url: '/stu/getStuInfo/' + data,
    method: 'get'
  })
}
// 更新信息

export function updateStuInfo(data) {
  return request({
    url: '/stu/updateStuInfo',
    method: 'put',
    data: data
  })
}

