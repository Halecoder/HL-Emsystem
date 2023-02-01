<template>
  <div class="stuInfo">
    <el-row
      :gutter="15"
      class="form shadow"
    >
      <div>学生信息</div>
      <el-form
        ref="elForm"
        :model="formData"
        :rules="rules"
        size="medium"
        label-width="100px"
        label-position="top"
      >

        <el-col :span="12">
          <el-form-item label="学号" prop="stuno">
            <el-input
              :value="username"
              placeholder="请输入学号"
              :maxlength="11"
              show-word-limit
              :disabled="true"
              clearable
              prefix-icon="el-icon-user"
              :style="{width: '100%'}"
            />

          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="姓名" prop="stuname">
            <el-input
              :value="name"
              placeholder="请输入姓名"
              :disabled="true"
              clearable
              prefix-icon="el-icon-s-custom"
              :style="{width: '100%'}"
            />
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="年龄" prop="age">
            <el-input
              v-model="formData.age"
              placeholder="请输入年龄"
              clearable
              prefix-icon="el-icon-user-solid"
              :style="{width: '100%'}"
            />
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label-width="5px" label="性别" prop="sex">
            <el-select v-model="formData.sex" placeholder="请选择性别" clearable :style="{width: '100%'}">
              <el-option
                v-for="(item, index) in sexOptions"
                :key="index"
                :label="item.label"
                :value="item.value"
                :disabled="item.disabled"
              />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="出生日期" prop="birthdate">
            <el-date-picker
              v-model="formData.birthdate"
              value-format="yyyy-MM-dd"
              :style="{width: '100%'}"
              placeholder="请选择出生日期"
              type="date"
              clearable
            />
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="民族" prop="nation">
            <el-select v-model="formData.nation" placeholder="请选择民族" clearable :style="{width: '100%'}">
              <el-option
                v-for="(item, index) in nationOptions"
                :key="index"
                :label="item.label"
                :value="item.value"
                :disabled="item.disabled"
              />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="政治面貌" prop="politic">
            <el-radio-group v-model="formData.politic" size="mini">
              <el-radio
                v-for="(item, index) in politicOptions"
                :key="index"
                :label="item.value"
                :disabled="item.disabled"
              >{{ item.label }}</el-radio>
            </el-radio-group>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="专业" prop="major">
            <el-input
              v-model="formData.major"
              placeholder="请输入专业"
              clearable
              :style="{width: '100%'}"
            />
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="培养方式" prop="cultivate">
            <el-select v-model="formData.cultivate" placeholder="请选择培养方式" clearable :style="{width: '100%'}">
              <el-option
                v-for="(item, index) in cultivateOptions"
                :key="index"
                :label="item.label"
                :value="item.value"
                :disabled="item.disabled"
              />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label-width="5px" label="学制" prop="sex">
            <el-select v-model="formData.academic" placeholder="请选择学制" clearable :style="{width: '100%'}">
              <el-option
                v-for="(item, index) in academicOptions"
                :key="index"
                :label="item.label"
                :value="item.value"
                :disabled="item.disabled"
              />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="院系名称" prop="facname">
            <el-input
              v-model="formData.facname"
              placeholder="请输入院系名称"
              clearable
              suffix-icon="el-icon-school"
              :style="{width: '100%'}"
            />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="学历" prop="degree">
            <el-radio-group v-model="formData.degree" size="medium">
              <el-radio
                v-for="(item, index) in degreeOptions"
                :key="index"
                :label="item.value"
                :disabled="item.disabled"
              >{{ item.label }}</el-radio>
            </el-radio-group>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="生源地" prop="birthplace">
            <el-cascader
              v-model="formData.birthplace"
              filterable
              separator=" "
              :options="options"
              placeholder="请输入生源地,可搜索"
              clearable
              :style="{width: '100%'}"
              @click="handleChange"
            />
          </el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item size="large">
            <el-button type="primary" @click="submitForm">提交</el-button>
            <el-button @click="resetForm">重置</el-button>
          </el-form-item>
        </el-col>
      </el-form>
    </el-row>
  </div>
</template>
<script>
import { addStuInfo, listStuInfo, updateStuInfo } from '@/api/stu/grastudent'
import Cookies from 'js-cookie'
import jwt_decode from 'jwt-decode'
import { getToken } from '@/utils/auth'
import { mapGetters } from 'vuex'
import '@/store/getters'
import { provinceAndCityData } from 'element-china-area-data'
import { parseTime, convertCodeAndText } from '@/utils/ruoyi'
import { callbackify } from 'util'

export default {
  components: {
  },
  props: [],
  data() {
    return {
      options: provinceAndCityData,
      formData: {
        active: 1,
        stuno: undefined,
        stuname: undefined,
        age: '',
        sex: '',
        birthdate: '',
        nation: undefined,
        politic: undefined,
        major: undefined,
        cultivate: undefined,
        facname: undefined,
        degree: undefined,
        birthplace: undefined,
        academic: undefined
      },
      rules: {
        age: [{
          pattern: /^(?:[1-9][0-9]?|1[20][0-9]|30)$/,
          message: '年龄不合法，请重新输入',
          trigger: 'blur'
        }],
        sex: [],
        birthdate: [],
        nation: [],
        politic: [],
        major: [],
        cultivate: [],
        facname: [],
        degree: [{
          required: true,
          message: '学历不能为空',
          trigger: 'change'
        }],
        birthplace: []
      },
      sexOptions: [{
        'label': '男',
        'value': '男'
      }, {
        'label': '女',
        'value': '女'
      }],
      academicOptions: [{
        'label': '2年',
        'value': '2年'
      }, {
        'label': '3年',
        'value': '3年'
      }, {
        'label': '4年',
        'value': '4年'
      }],
      nationOptions: [{
        'label': '汉族',
        'value': '汉族'
      }, {
        'label': '少数民族',
        'value': '少数民族'
      }],
      politicOptions: [{
        'label': '群众',
        'value': '群众'
      }, {
        'label': '团员',
        'value': '团员'
      }, {
        'label': '党员',
        'value': '党员'
      }, {
        'label': '预备党员',
        'value': '预备党员'
      }],
      cultivateOptions: [{
        'label': '定向',
        'value': '定向'
      }, {
        'label': '非定向',
        'value': '非定向'
      }],
      degreeOptions: [{
        'label': '专科',
        'value': '专科'
      }, {
        'label': '本科',
        'value': '本科'
      }, {
        'label': '研究生',
        'value': '研究生'
      }, {
        'label': '博士生',
        'value': '博士生'
      }],
      swiperOptions: {
        pagination: {
          el: '.swiper-pagination', // 与slot="pagination"处 class 一致
          clickable: true // 轮播按钮支持点击
        },
        navigation: {
          nextEl: '.swiper-button-next',
          prevEl: '.swiper-button-prev'
        }
      }
    }
  },
  computed: {
    ...mapGetters([
      'username',
      'name'
    ])
  },
  watch: {
  },
  created() {
    this.formData.stuname = this.$store.getters.name
    this.formData.stuno = this.$store.getters.username
    this.getList()
    const decode = jwt_decode(getToken())
    console.log(decode)
  },
  mounted() {},
  methods: {
    // 区域码转换
    handleChange(value) {
      console.log(convertCodeAndText(value))
      this.formData.birthplace = convertCodeAndText(value)
    },
    // 查询
    getList() {
      listStuInfo(this.formData.stuno).then(response => {
        this.loading = false
        if (response.data == null) {
          this.resetForm()
        } else {
          this.formData = response.data
          this.formData.birthplace = convertCodeAndText(this.formData.birthplace)
        }
        // 向父组件传值
        this.$emit('showStatus', response.data)
      }).finally(() => {
        this.loading = false
      }).catch(() => {})
    },
    // 更新
    upStuInfo() {
      this.buttonLoading = true
      updateStuInfo(this.formData).then(response =>
        this.$modal.msgSuccess('更新成功')
      ).finally(() => {
        this.buttonLoading = false
        this.getList()
      }).catch(() => {})
    },
    /** 提交按钮 */
    submitForm() {
      this.handleChange(this.formData.birthplace)
      console.log(this.formData)
      this.$refs['elForm'].validate(valid => {
        if (valid) {
          if (this.formData.stuId == null) {
            this.buttonLoading = true
            addStuInfo(this.formData).then(response =>
              this.$modal.msgSuccess('添加成功')
            ).finally(() => {
              this.getList()
            }).catch(() => {})
          } else {
            this.upStuInfo()
          }
        }
      })
    },
    resetForm() {
      this.$refs['elForm'].resetFields()
    },
    updateForm() {

    }
  }
}
</script>

  <style lang="scss" scoped>
    .form {
      height: 600px;
	// width: 300px;
	// height: 100px;
	background: #F8F8F9;
	border-radius: 10px;
	// margin: 10px;
  width: 700px;
  margin: 0 auto !important;
}

.shadow {
	position: relative;
	box-shadow: 0px 1px 4px rgba(0,0,0,0.3),
				0px 0px 20px rgba(0,0,0,0.1) inset;
}

.shadow::before,
.shadow::after {
   content:"";
   position:absolute;
   z-index:-1;
}

.shadow::before,
.shadow::after {
   content:"";
   position:absolute;
   z-index:-1;
   bottom:15px;
   left:10px;
   width:50%;
   height:20%;
}

.shadow::before,
.shadow::after {
   content:"";
   position:absolute;
   z-index:-1;
   bottom:15px;
   left:10px;
   width:50%;
   height:20%;
   box-shadow:0 15px 10px rgba(0, 0, 0, 0.7);
   transform:rotate(-3deg);
}

.shadow::after{
   right:10px;
   left:auto;
   transform:rotate(3deg);
 }
  </style>
