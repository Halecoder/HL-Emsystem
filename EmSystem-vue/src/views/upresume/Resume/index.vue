<template>
  <div>
    <el-row
      :gutter="15"
      class="form shadow"
    >
      <el-form
        ref="elForm"
        :model="formData"
        :rules="rules"
        size="medium"
        label-width="100px"
        label-position="top"
      >
        <el-col v-show="false" :span="12">
          <el-form-item label="学号" prop="stuno">
            <el-input
              v-model="formData.stuno"
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
        <el-col v-show="false" :span="12">
          <el-form-item label="姓名" prop="stuname">
            <el-input
              v-model="formData.stuname"
              placeholder="请输入姓名"
              :disabled="true"
              clearable
              :style="{width: '100%'}"
            />
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="毕业时间" prop="gradate">
            <el-date-picker
              v-model="formData.gradate"
              format="yyyy-MM-dd"
              value-format="yyyy-MM-dd"
              :style="{width: '100%'}"
              placeholder="请选择毕业时间"
              clearable
            />
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="求职" prop="job">
            <el-input
              v-model="formData.job"
              placeholder="请输入期望职位"
              clearable
              prefix-icon="el-icon-office-building"
              :style="{width: '100%'}"
            />
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="外语语种" prop="language">
            <el-select v-model="formData.language" placeholder="请选择外语语种" clearable :style="{width: '100%'}">
              <el-option
                v-for="(item, index) in languageOptions"
                :key="index"
                :label="item.label"
                :value="item.value"
                :disabled="item.disabled"
              />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="外语等级" prop="level">
            <el-select v-model="formData.level" placeholder="请选择外语等级" clearable :style="{width: '100%'}">
              <el-option
                v-for="(item, index) in levelOptions"
                :key="index"
                :label="item.label"
                :value="item.value"
                :disabled="item.disabled"
              />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="电脑水平" prop="computer">
            <el-select v-model="formData.computer" placeholder="请选择电脑水平" clearable :style="{width: '100%'}">
              <el-option
                v-for="(item, index) in computerOptions"
                :key="index"
                :label="item.label"
                :value="item.value"
                :disabled="item.disabled"
              />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="联系电话" prop="phone">
            <el-input
              v-model="formData.phone"
              placeholder="请输入联系电话"
              clearable
              prefix-icon="el-icon-phone"
              :style="{width: '100%'}"
            />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="联系邮箱" prop="mail">
            <el-input
              v-model="formData.mail"
              placeholder="请输入联系邮箱"
              clearable
              prefix-icon="el-icon-chat-dot-square"
              :style="{width: '100%'}"
            />
          </el-form-item>
        </el-col>
        <el-col :span="12" class="swiper-no-swiping">
          <el-form-item label="联系地址" prop="address">
            <el-input
              v-model="formData.address"
              placeholder="请输入联系地址"
              clearable
              prefix-icon="el-icon-s-home"
              :style="{width: '100%'}"
            />
          </el-form-item>
        </el-col>
        <el-col :span="12" class="swiper-no-swiping">
          <el-form-item label="出生地" prop="city">
            <el-cascader
              v-model="formData.city"
              filterable
              separator=" "
              :options="options"
              placeholder="请输入出生地"
              clearable
              prefix-icon="el-icon-s-home"
              :style="{width: '100%'}"
              @click="handleChange"
            />
          </el-form-item>
        </el-col>
        <!-- <el-col :span="12" class="swiper-no-swiping">
          <el-form-item label="曾任职务" prop="office">
            <el-input
              v-model="formData.office"
              type="textarea"
              placeholder="请输入曾任职务"
              :maxlength="100"
              show-word-limit
              :autosize="{minRows: 1, maxRows: 10}"
              :style="{width: '100%'}"
            />
          </el-form-item>
        </el-col> -->
        <el-col :span="12" class="swiper-no-swiping">
          <el-form-item label="个人介绍" prop="about">
            <el-input
              v-model="formData.about"
              type="textarea"
              placeholder="请输入个人介绍"
              :maxlength="200"
              :autosize="{minRows: 11, maxRows: 11}"
              show-word-limit
              :style="{width: '100%'}"
            />
          </el-form-item>
        </el-col>
        <el-col :span="12" class="swiper-no-swiping">
          <el-form-item label="个人优点" prop="knowledge" style="width:100%;">
            <el-input
              v-model="formData.knowledge"
              type="textarea"
              placeholder="用一句话介绍你的优点"
              maxlength="20"
              :autosize="{minRows: 1, maxRows: 2}"
              show-word-limit
            />
          </el-form-item>
        </el-col>
        <el-col :span="12" class="swiper-no-swiping">
          <el-form-item label="个人网站" prop="website">
            <el-input
              v-model="formData.website"
              placeholder="请输入网站链接"
              clearable
              prefix-icon="el-icon-s-home"
              :style="{width: '100%'}"
            />
          </el-form-item>
        </el-col>
        <el-col :span="12" class="swiper-no-swiping">
          <el-form-item label="个人github" prop="github">
            <el-input
              v-model="formData.github"
              placeholder="请输入github名称"
              clearable
              prefix-icon="el-icon-s-home"
              :style="{width: '100%'}"
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

import { getToken } from '@/utils/auth'
import { mapGetters } from 'vuex'
import '@/store/getters'
import { addResume, listResume, updateResume } from '@/api/stu/resume'
import { provinceAndCityData } from 'element-china-area-data'
import { parseTime, convertCodeAndText } from '@/utils/ruoyi'

export default {
  components: { ...mapGetters([
    'username',
    'name'
  ]) },
  props: [],
  data() {
    return {
      options: provinceAndCityData,
      formData: {
        stuno: undefined,
        stuname: undefined,
        gradate: null,
        job: undefined,
        language: undefined,
        level: undefined,
        computer: undefined,
        phone: undefined,
        mail: undefined,
        address: undefined,
        office: undefined,
        about: undefined,
        city: undefined,
        website: undefined,
        github: undefined,
        knowledge: undefined
      },
      rules: {
        stuno: [{
          required: true,
          message: '请输入学号',
          trigger: 'blur'
        }],
        stuname: [{
          required: true,
          message: '请输入姓名',
          trigger: 'blur'
        }],
        gradate: [{
          required: true,
          message: '请选择毕业时间',
          trigger: 'change'
        }],
        job: [{
          required: true,
          message: '请输入期望职位',
          trigger: 'blur'
        }],
        language: [{
          required: true,
          message: '请选择外语语种',
          trigger: 'change'
        }],
        level: [{
          required: true,
          message: '请选择外语等级',
          trigger: 'change'
        }],
        computer: [{
          required: true,
          message: '请选择电脑水平',
          trigger: 'change'
        }],
        phone: [{
          pattern: /^1[34578]\d{9}$/,
          message: '请输入正确手机号',
          trigger: 'blur'
        }],
        mail: [{
          pattern: /^[A-Za-z0-9]+([_.][A-Za-z0-9]+)*@([A-Za-z0-9-]+\.)+[A-Za-z]{2,6}$/,
          message: '请输入合法邮箱',
          trigger: 'blur'
        }],
        address: [],
        office: [],
        city: [{
          required: true,
          message: '请输入出生地',
          trigger: 'blur'
        }]
        // experience: [{
        //   required: true,
        //   message: '工作经历不能为空',
        //   trigger: 'blur'
        // }]
      },
      languageOptions: [{
        'label': '英语',
        'value': '英语'
      }, {
        'label': '其它小语种',
        'value': '小语种'
      }],
      levelOptions: [{
        'label': '未过级',
        'value': '未过级'
      }, {
        'label': '四级',
        'value': '四级'
      }, {
        'label': '六级',
        'value': '六级'
      }],
      computerOptions: [{
        'label': '二级',
        'value': '二级'
      }, {
        'label': '二级以上',
        'value': '二级以上'
      }]
    }
  },
  computed: {
    ...mapGetters([
      'username',
      'name'
    ]) },
  watch: {},
  created() {
    this.formData.stuname = this.$store.getters.name
    this.formData.stuno = this.$store.getters.username
    this.getList()
  },
  mounted() {
  },
  methods: {
    // 区域码转换
    handleChange(value) {
      console.log(convertCodeAndText(value))
      this.formData.city = convertCodeAndText(value)
    },

    // 查询
    getList() {
      listResume(this.formData.stuno).then(response => {
        this.loading = false
        if (response.data == null) {
          this.resetForm()
        } else {
          this.formData = response.data
          this.formData.city = convertCodeAndText(this.formData.city)
        }
        this.loading = false
        this.$emit('showStatus', response.data)
      }).finally(() => {
        this.loading = false
      }).catch(() => {})
    },

    // 更新
    upResume() {
      this.buttonLoading = true
      updateResume(this.formData).then(response =>
        this.$modal.msgSuccess('更新成功')
      ).finally(() => {
        this.buttonLoading = false
        this.getList()
      }).catch(() => {})
    },

    // 提交表单
    submitForm() {
      this.handleChange(this.formData.city)
      this.$refs['elForm'].validate(valid => {
        if (valid) {
          if (this.formData.resid == null) {
            this.buttonLoading = true
            addResume(this.formData).then(response =>
              this.$modal.msgSuccess('添加成功')
            ).finally(() => {
              this.getList()
            }).catch(() => {})
          } else {
            this.upResume()
          }
        }
      })
    },
    resetForm() {
      this.$refs['elForm'].resetFields()
    }
  }
}

</script>
<style lang="scss" scoped>

.form {
// width: 300px;
// height: 100px;
height: 600px;
background: #F8F8F9;
border-radius: 10px;
width: 700px;
  margin: 0 auto !important;
  overflow-y: scroll;

}

.shadow {
position: relative;
max-width: 80%;
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

.el-icon-circle-plus-outline{
  cursor: pointer;
  color: #1890ff;

}

// 样式穿透
::v-deep .el-textarea .el-input__count {
    color: #909399;
    background: transparent !important;
    bottom: -10px;
}

</style>

