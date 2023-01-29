import request from '@/utils/request'

// 查询测试树表列表
export function listTree() {
  return request({
    url: '/demo/tree/list',
    method: 'get'
    // params: query
  })
}

// 查询菜单下拉树结构
export function treeselect() {
  return request({
    url: '/demo/tree/treeselect',
    method: 'get'
  })
}

// 根据角色ID查询菜单下拉树结构
export function roleMenuTreeselect(roleId) {
  return request({
    url: '/demo/tree/roleTreeSelect/' + roleId,
    method: 'get'
  })
}

// 查询树表详细
export function getTree(nodeId) {
  return request({
    url: '/demo/tree/' + nodeId,
    method: 'get'
  })
}

// 新增测试树表
export function addTree(data) {
  return request({
    url: '/demo/tree/add',
    method: 'post',
    data: data
  })
}

// 修改测试树表
export function updateTree(data) {
  return request({
    url: '/demo/tree',
    method: 'put',
    data: data
  })
}

// 删除测试树表
export function delTree(id) {
  return request({
    url: '/demo/tree/' + id,
    method: 'delete'
  })
}
