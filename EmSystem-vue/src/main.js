import Vue from 'vue'

import 'normalize.css/normalize.css' // A modern alternative to CSS resets

import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import './assets/styles/element-variables.scss'
// import locale from 'element-ui/lib/locale/lang/en' // lang i18n

import 'monaco-editor/min/vs/editor/editor.main.css'

import '@/assets/styles/index.scss' // global css
import '@/assets/styles/ruoyi.scss' // ruoyi css

import App from './App'
import store from './store'
import router from './router'
import plugins from './plugins' // plugins
import { download } from '@/utils/request'

import './assets/icons' // icon
import './permission' // permission control

import { handleTree, resetForm } from '@/utils/ruoyi'

// 分页组件
import Pagination from '@/components/Pagination'
// 自定义表格工具组件
import RightToolbar from '@/components/RightToolbar'
// 富文本组件
import Editor from '@/components/Editor'
// 编辑器组件
import Tinymce from '@/components/tinymce'

// import VueMeta from 'vue-meta'
Vue.use(plugins)

// Vue.use(VueMeta)
// 全局方法挂载
Vue.prototype.handleTree = handleTree
Vue.prototype.resetForm = resetForm
Vue.component('Pagination', Pagination)
Vue.component('RightToolbar', RightToolbar)
Vue.prototype.download = download
Vue.component('Editor', Editor)
Vue.component('tinymce', Tinymce)

// set ElementUI lang to EN
// Vue.use(ElementUI, { locale })
// 如果想要中文版 element-ui，按如下方式声明
Vue.use(ElementUI)

Vue.config.productionTip = false

new Vue({
  el: '#app',
  router,
  store,
  render: h => h(App)
})
