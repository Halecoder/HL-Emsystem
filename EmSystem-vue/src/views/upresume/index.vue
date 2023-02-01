<template>
  <div class="resume">
    <div class="steps">
      <el-steps :active="active" direction="vertical" align-center>
        <el-step title="上传信息" description="填写学生信息   " />
        <el-step title="填写简历" description="填写简历信息" />
        <el-step title="完善简历" description="完善简历信息" />
      </el-steps>
    </div>
    <!-- v-scroll-lock="true" -->
    <div class="swiper swiper-no-swiping">
      <swiper ref="mySwiper" v-scroll-lock="true" :options="swiperOptions">
        <swiper-slide>
          <StuInfo @showStatus="showStatus" />
        </swiper-slide>
        <swiper-slide>
          <Resume />
        </swiper-slide>
        <swiper-slide>
          <Other />
        </swiper-slide>

        <div v-show="isShow" slot="button-prev" class="swiper-button-prev" @click="pre" />
        <div v-show="isShow" slot="button-next" class="swiper-button-next" @click="next" />
        <div slot="pagination" class="swiper-pagination" />
      </swiper>
    </div>
  </div>
</template>
<script>

import { swiper, swiperSlide } from 'vue-awesome-swiper'
// 导入swiper
import 'swiper/css/swiper.css'

// 组件注册 注意别加{}
import StuInfo from './StuInfo'
import Resume from './Resume'
import Other from './Other'

export default {
  components: {
    swiper,
    swiperSlide,
    'StuInfo': StuInfo,
    Resume,
    Other
  },
  props: [],
  data() {
    return {
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
    this.timer = new Date().getTime()
  },
  mounted() {},
  methods: {
    showStatus(val) {
      if (val == null) {
        this.isShow = false
      } else {
        this.isShow = true
      }
    },
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
    text-align: center;
    // height: 80%;
    margin-top: 30px;
}
.swiper-pagination{
  margin-top: 30px;
  position: relative;
}

.swiper{
    // width: 80%;
    margin: auto;
}
.steps{
  float: left;
    height: 100%;
    line-height: 100%;
    margin-top: 100px;
    position: absolute;
    margin-left: 50px;
    position: fixed;
    // margin: auto;
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
