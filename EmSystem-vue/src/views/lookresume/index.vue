<template>
  <div class="resumes">

    <div class="example-3d" @contextmenu.prevent="onContextmenu">
      <swiper
        ref="mySwiper"
        class="swiper"
        :options="swiperOption"
      >
        <swiper-slide v-for="(resume,index ) in resumes" :key="index">
          <router-link :to="`/${username}/resumes/${resume.resumeName}`">
            <img
              :src="resume.resume"
              alt=""
              :data-index="`${resume.resumeName}`"
              @mouseenter="onMouseEnter"
              @mouseleave="onMouseLeave"
            >
          </router-link>
        </swiper-slide>
        <div slot="pagination" class="swiper-pagination" />
      </swiper>
    </div>
  </div>
</template>

<script>

import { swiper, swiperSlide } from 'vue-awesome-swiper'
// 导入swiper
import 'swiper/css/swiper.css'

// import './scripts/export'

import { getFileNames } from '@/utils/ruoyi'

import { toImages, downloadResumeByType } from '@/api/stu/resume'

import $ from 'jquery'

import { mapGetters } from 'vuex'

var Name

export default {
  name: 'SwiperExample3dCoverflow',
  title: '3D Coverflow effect',

  components: {
    swiper,
    swiperSlide
  },
  props: [],
  data() {
    return {
      username: '',
      resumeNames: [],
      resumes: [],
      swiperOption: {
        // effect: 'coverflow',
        grabCursor: true,
        centeredSlides: true,
        slidesPerView: '3',
        coverflowEffect: {
          rotate: 50,
          stretch: 0,
          depth: 100,
          modifier: 1,
          slideShadows: true
        },
        pagination: {
          el: '.swiper-pagination',
          clickable: true // 允许分页点击跳转
        },
        loop: true, // 开启循环模式
        autoplay: {
          delay: 3000,
          disableOnInteraction: false // 当用户滑动图片后继续自动轮播
        }
      },
      downloadParams: {
        Type: '' // 下载类型
      }

    }
  },
  computed: {
    // 获取可计算的swiper
    mySwiper() {
      // mySwiper 是要绑定到标签中的ref属性
      return this.$refs.mySwiper.swiper
    },
    ...mapGetters([
      'username'
    ])

  },
  watch: {},
  created() {
    this.username = this.$store.getters.username
    this.$data.resumeNames = getFileNames()
    this.resumeNames.forEach(resume => {
      this.resumes.push({ resume: require(`./images/${this.username}/${resume}.png`), resumeName: `${resume}` })
    })
  },
  mounted() {},
  methods: {

    /** 鼠标移入 */
    onMouseEnter() {
      $('.swiper-slide').hover(function(e) {
        Name = e.target.dataset.index
      })
      return this.mySwiper.autoplay.stop()
    },

    // 鼠标移出
    onMouseLeave() {
      // console.log(this.mySwiper)
      return this.mySwiper.autoplay.start()
    },
    updateResumes() {
      this.loading = true
      toImages(this.resumeNames).then(response => {
      }).finally(() => {
        this.loading = false
      }).catch(() => {})
    },

    downloadResumeByPdf() {
      this.downloadParams.Type = 'pdf'
      this.download('/stu/download/resume/' + `${Name}`, {
        ...this.downloadParams
      }, `${this.username}_${Name}.pdf`)
    },

    downloadResumeByPng() {
      this.downloadParams.Type = 'png'
      this.download('/stu/download/resume/' + `${Name}`, {
        ...this.downloadParams
      }, `${this.username}_${Name}.png`)
    },
    // 自定义右键菜单
    onContextmenu(event) {
      this.$contextmenu({
        items: [
          { label: '刷新(R)', divided: true, icon: 'el-icon-refresh', onClick: this.updateResumes },
          { label: '下载PDF(D)...', icon: 'el-icon-download', onClick: this.downloadResumeByPdf },
          { label: '另存为图片(S)...', icon: 'el-icon-s-promotion', onClick: this.downloadResumeByPng }
        ],
        event, // 鼠标事件信息
        customClass: 'custom-class', // 自定义菜单 class
        zIndex: 3, // 菜单样式 z-index
        minWidth: 230 // 主菜单最小宽度
      })
      return false
    }

  },

  // 不同路由界面设置不同body颜色
  beforeRouteEnter(to, from, next) {
    window.document.body.style.backgroundColor = '#CCCCCC'
    next()
  },

  beforeRouteLeave(to, from, next) {
    window.document.body.style.backgroundColor = ''
    next()
  }

}
</script>

<style lang="scss" scoped>

.button{
  float: right;
  margin-top: 60px;
  margin-right: 60px;

}

img{
  width:450px;
  height: 100%;
}

.example-3d {
    width: 100%;
    height: 600px;
    padding-top: 30px;
    padding-bottom: 10px;
  }

  .swiper {
    height: 100%;
    width: 100%;

    .swiper-slide {
       text-align: center;
    }

    .swiper-pagination {
      ::deep .swiper-pagination-bullet.swiper-pagination-bullet-active {
        background-color: white;
      }
    }
  }

  .custom-class .menu_item__available:hover,
.custom-class .menu_item_expand {
  background: #ffecf2 !important;
  color: hsl(208, 100%, 63%) !important;
}

</style>

