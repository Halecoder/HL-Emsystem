<template>
  <div class="page-wrapper">
    <div :id="$route.params.resumeid" class="page">
      <div class="page-inner">
        <component :is="$route.params.resumeid" />
      </div>
    </div>
  </div>
</template>

<script>
import Vue from 'vue'
import '../resumes/resumes'
export default Vue.component('resume', {
  name: 'App',

  // 不同路由界面设置不同body颜色
  beforeRouteEnter(to, from, next) {
    window.document.body.style.backgroundColor = '#CCCCCC'
    next()
  },

  beforeRouteLeave(to, from, next) {
    window.document.body.style.backgroundColor = ''
    next()
  }
})

</script>

  <style lang="scss" scoped>
  @import "@/assets/styles/resume.scss";  //引入方式
  .page-inner{
    height: 100%;
    width: 100%;
  }
  .page-wrapper {
    overflow-x: hidden;
    background: #CCCCCC;
    margin: 0;
    padding: 0;
    -webkit-print-color-adjust: exact;
    box-sizing: border-box;
  }

  .resume {
    height: 100%;
    width: 100%;
  }

  .page {
    background: white;
    position: relative;
    width: 21cm;
    height: 29.68cm;
    display: block;
    page-break-after: auto;
    overflow: hidden;
  }
  </style>
