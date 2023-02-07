
import { constantRouterMap } from '@/router/index'
import { parse_menu } from '@/router/menu_tree'

const permission = {
  state: {
    routers: constantRouterMap,
    addRouters: []
  },
  mutations: {
    SET_ROUTERS: (state, routers) => {
      state.addRouters = routers
      state.routers = constantRouterMap.concat(routers)
      console.log('路由这里')
      console.log(state.routers)
    }
  },
  actions: {
    generateRoutes({ commit }, data) {
      return new Promise(resolve => {
        // const { node, children } = data
        const accessedRouters = parse_menu(data)
        commit('SET_ROUTERS', accessedRouters)
        resolve(accessedRouters)
      })
    }
  }
}

export default permission
