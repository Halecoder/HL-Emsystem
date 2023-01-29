<template>
  <div v-if="bannerData.length" class="m-banner-wrap" :style="`width: ${width}; height: ${height}`">
    <div class="m-banner-list">
      <div
        v-for="(item, index) in bannerData"
        v-show="index===activeIndex"
        :key="index"
        class="u-banner-item fade"
        :style="`background: url(${item.imgUrl}) no-repeat center; background-size: cover;`"
        @mouseenter="onStop"
        @mouseleave="onStart"
      >
        <a :href="item.link" target="_blank" />
      </div>
    </div>
    <div v-if="bannerData.length > 1" class="m-dot-list">
      <div v-for="n of bannerData.length" :key="n" :class="['u-dot-item', {active: (n-1)===activeIndex}]" @mouseenter="onEnter(n-1)" />
    </div>
  </div>
</template>
<script>
export default {
  name: 'Banner',
  props: {
    bannerData: { // banner数组
      type: Array,
      default: () => {
        return []
      }
    },
    interval: { // 切换间隔ms
      type: Number,
      default: 3000
    },
    width: { // 宽度
      type: String,
      default: '100%'
    },
    height: { // 高度
      type: String,
      default: '600px'
    }
  },
  data() {
    return {
      activeIndex: 0,
      timer: null
    }
  },
  mounted() {
    setTimeout(() => {
      this.onStart()
    }, 1000)
    window.onfocus = () => { // 页面激活
      this.onStart()
    }
    window.onblur = () => { // 页面失焦
      this.onStop()
    }
  },
  beforeDestroy() {
    clearInterval(this.timer)
    this.timer = null
  },
  methods: {
    onStart() {
      clearInterval(this.timer)
      if (this.bannerData.length > 1) {
        this.timer = setInterval(this.onMove, this.interval)
      }
    },
    onStop() {
      clearInterval(this.timer)
    },
    onEnter(index) {
      clearInterval(this.timer)
      this.activeIndex = index
      this.onStart()
    },
    onMove() {
      if (this.activeIndex === this.bannerData.length - 1) {
        this.activeIndex = 0
      } else {
        this.activeIndex++
      }
    }
  }
}
</script>
  <style lang="less" scoped>
  @themeColor: #1890FF;
  .m-banner-wrap {
    width: 100%;
    height: 585px;
    min-width: 1200px;
    margin: 0 auto;
    overflow: hidden;
    position: relative;
    z-index: 9;
    .m-banner-list {
      height: 100%;
      .u-banner-item {
        min-width: 1200px;
        height: 100%;
        width: 100%;
        a {
          display: block;
          height: 100%;
        }
      }
      .fade { // 切换banner时的过渡效果
        -webkit-animation: fadein 0.3s cubic-bezier(0.390, 0.575, 0.565, 1.000) both;
        animation: fadein 0.3s cubic-bezier(0.390, 0.575, 0.565, 1.000) both;
      }
      @-webkit-keyframes fadein {
        0% {
          opacity: 0;
        }
        100% {
          opacity: 1;
        }
      }
      @keyframes fadein {
        0% {
          opacity: 0;
        }
        100% {
          opacity: 1;
        }
      }
    }
    .m-dot-list {
      width: 100%;
      line-height: 4px;
      position: absolute;
      bottom: 65px;
      text-align: center;
      .u-dot-item { // 圆点样式
        display: inline-block;
        width: 36px;
        height: 4px;
        background: #E3E3E3;
        border-radius: 1px;
        vertical-align: bottom;
        cursor: pointer;
        &:not(:last-child) {
          margin-right: 10px;
        }
      }
      .active { // 圆点选中样式
        background: @themeColor;
      }
    }
  }
  </style>
