<template>
  <div class="resume">
    <!-- v-scroll-lock="true" -->
    <div class="swiper swiper-no-swiping">
      <swiper ref="mySwiper" :options="swiperOptions">
        <swiper-slide>
          <component :is="item.app" v-for="item in apps" />
        </swiper-slide>
        <swiper-slide>
          <component :is="item.app" v-for="item in apps" />
        </swiper-slide>
        <swiper-slide>
          <component :is="item.app" v-for="item in apps" />
        </swiper-slide>

        <div slot="button-prev" class="swiper-button-prev" @click="pre" />
        <div slot="button-next" class="swiper-button-next" @click="next" />
        <div slot="pagination" class="swiper-pagination" />
      </swiper>
    </div>
  </div>
</template>
<script>

import { swiper, swiperSlide } from 'vue-awesome-swiper'
// 导入swiper
import 'swiper/css/swiper.css'

import { getFileNames } from '@/utils/ruoyi'

// 组件注册 注意别加{}

import './resumes/resumes'

export default {
  components: {
    swiper,
    swiperSlide

  },
  props: [],
  data() {
    return {
      comps: [],
      apps: [],
      timer: '',
      active: 1,
      isShow: false,
      swiperOptions: {
        pagination: {
          el: '.swiper-pagination',
          type: 'fraction'
        },
        navigation: {
          nextEl: '.swiper-button-next',
          prevEl: '.swiper-button-prev'
        }
      }
    }
  },
  computed: {

  },
  watch: {
  },
  created() {
    // 获取vue文件名
    this.$data.comps = getFileNames()
    this.comps.forEach(app => {
      this.apps.push({ app: require(`./resumes/${app}.vue`).default })
    })
  },
  mounted() {
  },
  methods: {
    next() {
      this.active++
    },
    pre() {
      this.active--
    }
  }
}

</script>

  <style lang="scss" scoped>

.swiper-slide {
    // text-align: center;
    // height: 80%;
    margin-top: 30px;
}
.swiper-pagination{
  margin-top: 30px;
  position: relative;
}

.swiper{
     width: 600px;
     height: 100px;
    margin: auto;
}
.swiper-button-prev{
  top: 0px;
  left: -30%;
    right: 30%;
    bottom: 0px;
    margin: auto;
}
.swiper-button-next{
  top: 0px;
  left: 30%;
    right: -30%;
    bottom: 0px;
    margin: auto;
}

  </style>
