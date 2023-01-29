<template>
  <div class="test-form">
    <parser :form-conf="formConf" @submit="sumbitForm1" />
    <parser :key="key2" :form-conf="formConf" @submit="sumbitForm2" />
    <el-button @click="change">
      change
    </el-button>
  </div>
</template>

<script>
import Parser from '../Parser'

// 若parser是通过安装npm方式集成到项目中的，使用此行引入
// import Parser from 'form-gen-parser'

export default {
  components: {
    Parser
  },
  props: {},
  data() {
    return {
      key2: +new Date(),
      formConf: {
        fields: [
          {
            __config__: {
              label: '单行文本',
              labelWidth: null,
              showLabel: true,
              changeTag: true,
              tag: 'el-input',
              tagIcon: 'input',
              required: true,
              layout: 'colFormItem',
              span: 12,
              document: 'https://element.eleme.cn/#/zh-CN/component/input',
              regList: [],
              formId: 101,
              renderKey: 1590373683447
            },
            __slot__: {
              prepend: '',
              append: ''
            },
            placeholder: '请输入单行文本单行文本',
            style: {
              width: '100%'
            },
            clearable: true,
            'prefix-icon': '',
            'suffix-icon': '',
            maxlength: null,
            'show-word-limit': false,
            readonly: false,
            disabled: false,
            __vModel__: 'field101'
          },
          {
            __config__: {
              label: '按钮',
              showLabel: false,
              changeTag: true,
              labelWidth: null,
              tag: 'el-button',
              tagIcon: 'button',
              span: 12,
              layout: 'colFormItem',
              document: 'https://element.eleme.cn/#/zh-CN/component/button',
              formId: 102,
              renderKey: 1590373687338
            },
            __slot__: {
              default: '主要按钮'
            },
            type: 'warning',
            icon: 'el-icon-search',
            round: false,
            size: 'medium',
            plain: false,
            circle: false,
            disabled: false,
            __vModel__: 'field102'
          }
        ],
        formRef: 'elForm',
        formModel: 'formData',
        size: 'medium',
        labelPosition: 'left',
        labelWidth: 100,
        formRules: 'r',
        gutter: 27,
        disabled: false,
        span: 12,
        formBtns: false,
        unFocusedComponentBorder: false
      }
    }
  },
  computed: {},
  watch: {},
  created() {},
  mounted() {},
  methods: {
    change() {
      this.key2 = +new Date()
      const t = this.formConf
      this.formConf = this.formConf2
      this.formConf2 = t
    },
    sumbitForm1(data) {
      console.log('sumbitForm1提交数据：', data)
    },
    sumbitForm2(data) {
      console.log('sumbitForm2提交数据：', data)
    }
  }
}
</script>

<style lang="scss" scoped>
.test-form {
  margin: 15px auto;
  width: 800px;
  padding: 15px;
}
</style>
