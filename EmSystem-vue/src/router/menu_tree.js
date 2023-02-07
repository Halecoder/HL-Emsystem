import Layout from '@/layout'
// 获取resume所有文件名
import { getFileNames } from '@/utils/ruoyi'

// data 就是后台传过来的菜单数据，这个数据直接是一个集合就好了，不需要构建菜单树
export function parse_menu(data) {
  const result = getPage(data)
  result.push({ path: '*', redirect: '/404', hidden: true })
  return result
}

/**
   * 构建菜单
   *
   *
   * @param allList
   * @returns {[]}
   */
function getPage(allList) {
  // 构建的菜单数据
  const menuList = []
  // 获取所有的父级菜单
  allList.forEach((menu, index) => {
    if (menu.parentId === 0) {
      let temp
      if (menu.nodeType === 1) {
        temp = {
          menuId: menu.nodeId,
          parentId: menu.parentId,
          // 为每个菜单项绑上唯一标识符 menu.nodeId
          path: menu.url === '' ? '/' + menu.nodeId : menu.url,
          name: menu.nodeName,
          component: Layout,
          redirect: '/dashboard',
          meta: { title: menu.nodeName, icon: menu.icon ? menu.icon : 'form' },
          children: []
        }
      } else {
        temp = {
          menuId: menu.nodeId,
          parentId: menu.parentId,
          path: menu.url === '' ? '/' + menu.nodeId : menu.url,
          name: menu.nodeName,
          redirect: '/dashboard',
          // component: resolve => require([`@/views/${menu.url}/index.vue`], resolve),
          component: Layout,
          meta: { title: menu.nodeName, icon: menu.icon ? menu.icon : 'form' },
          children: []
        }
      }
      menuList.push(temp)
    }
  })

  menuList.forEach((menu, index) => {
    menu.children = menu.children.concat(getChild(menu.menuId, allList))
  })

  console.log(menuList)
  return menuList
}

/**
   *  构建子菜单数据
   *
   * @param menuId
   * @param menuArr
   * @returns {[]}
   */
function getChild(menuId, allList) {
  const chileArr = []
  allList.forEach((menu, index) => {
    // 遍历所有节点，将父菜单id与传过来的id比较
    if (menu.parentId !== 0) {
      if (menu.parentId === menuId) {
        let temp
        if (menu.nodeType === 2) {
          temp = {
            menuId: menu.nodeId,
            parentId: menu.parentId,
            path: menu.url,
            name: menu.nodeName,
            component: resolve => require([`@/views/${menu.url}/index.vue`], resolve),
            meta: { title: menu.nodeName, icon: menu.icon ? menu.icon : 'form' }
          }
        }
        chileArr.push(temp)
      }
    }
  })
  return chileArr
}

import resume from '@/views/lookresume/resumeUrl'

// 生成新简历路由
export function generateResumeRoutes() {
  const resumesRoutes = []
  getFileNames().forEach((resumeName, index) => {
    let resumeRoute
    if (resumeName !== null) {
      resumeRoute = {
        path: '/resumes/:resumeid',
        component: resume,
        name: 'resumeName'

      }
      resumesRoutes.push(resumeRoute)
    }
  })
  return resumesRoutes
}
