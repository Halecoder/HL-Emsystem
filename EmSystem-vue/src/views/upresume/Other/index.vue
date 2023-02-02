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
        <el-col :span="24">
          <el-form-item v-for="(item,index) in formData.education" prop="education">
            <span slot="label">
              <el-tooltip content="继续添加教育经历" placement="left">
                <i class="el-icon-circle-plus-outline" @click="addEducation" />
              </el-tooltip>
              <h2>教育经历</h2>
              <el-tooltip v-if="index>0" content="删除教育经历" placement="right">
                <i class="el-icon-remove-outline" @click="removeEducation(index)" />
              </el-tooltip>
            </span>
            <el-select v-model="formData.education[index].degree" clearable placeholder="学位信息" style="width:60%;">

              <el-option
                v-for="(item, index) in degreeOptions"
                :key="index"
                :label="item.label"
                :value="item.value"
                :disabled="item.disabled"
              />
            </el-select>

            <el-input
              v-model="formData.education[index].description"
              placeholder="毕业学校"
              style="width:60%;padding-top:10px;"
            />
            <el-date-picker
              v-model="formData.education[index].timeperiod"
              type="daterange"
              format="yyyy-MM-dd"
              value-format="yyyy-MM-dd"
              style="width:60%;margin-top:10px;"
              start-placeholder="开始日期"
              end-placeholder="结束日期"
              range-separator="至"
              clearable
            />
            <el-input
              v-model="formData.education[index].website"
              placeholder="学校官网"
              style="width:60%;padding-top:10px;"
            />
          </el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item v-for="(project,index) in formData.projects" prop="projects">
            <span slot="label">
              <el-tooltip content="继续添加项目经历" placement="left">
                <i class="el-icon-circle-plus-outline" @click="addProject" />
              </el-tooltip>
              <h2>项目经历</h2>
              <el-tooltip v-if="index>0" content="删除项目经历" placement="right">
                <i class="el-icon-remove-outline" @click="removeProject(index)" />
              </el-tooltip>
            </span>
            <el-input
              v-model="formData.projects[index].name"
              placeholder="项目名称"
              style="width:60%;"
            />
            <el-input
              v-model="formData.projects[index].platform"
              placeholder="项目平台"
              style="width:60%;padding-top:10px;"
            />
            <el-date-picker
              v-model="formData.projects[index].timeperiod"
              format="yyyy-MM-dd"
              value-format="yyyy-MM-dd"
              placeholder="请输入大概日期"
              style="width:60%;margin-top:10px;"
              clearable
            />
            <el-input
              v-model="formData.projects[index].description"
              placeholder="项目描述"
              rows="4"
              type="textarea"
              maxlength="200"
              show-word-limit
              style="width:60%;padding-top:10px;"
            />
            <el-input
              v-model="formData.projects[index].url"
              placeholder="项目链接"
              style="width:60%;padding-top:10px;"
            />
          </el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item v-for="(experience,index) in formData.experience" prop="experience">
            <span slot="label">
              <el-tooltip content="继续添加工作经历" placement="left">
                <i class="el-icon-circle-plus-outline" @click="addExperience" />
              </el-tooltip>
              <h2>工作经历</h2>
              <el-tooltip v-if="index>0" content="删除工作经历" placement="right">
                <i class="el-icon-remove-outline" @click="removeExperience(index)" />
              </el-tooltip>
            </span>
            <el-input
              v-model="formData.experience[index].company"
              placeholder="公司名称"
              style="width:60%;"
            />
            <el-input
              v-model="formData.experience[index].position"
              placeholder="你的职位"
              style="width:60%;padding-top:10px;"
            />
            <el-date-picker
              v-model="formData.experience[index].timeperiod"
              type="daterange"
              format="yyyy-MM-dd"
              value-format="yyyy-MM-dd"
              style="width:60%;margin-top:10px;"
              start-placeholder="开始日期"
              end-placeholder="结束日期"
              range-separator="至"
              clearable
            />

            <el-input
              v-model="formData.experience[index].website"
              placeholder="公司官网"
              style="width:60%;padding-top:10px;"
            />
            <el-input
              v-model="formData.experience[index].description"
              placeholder="工作描述"
              rows="4"
              type="textarea"
              maxlength="200"
              show-word-limit
              style="width:60%;padding-top:10px;"
            />
          </el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item v-for="(skill,index) in formData.skills" prop="skills">
            <span slot="label">
              <el-tooltip content="继续添加技术栈" placement="left">
                <i class="el-icon-circle-plus-outline" @click="addSkill" />
              </el-tooltip>
              <h2>技术栈</h2>
              <el-tooltip v-if="index>0" content="删除工作经历" placement="right">
                <i class="el-icon-remove-outline" @click="removeSkill(index)" />
              </el-tooltip>
            </span>
            <el-input
              v-model="formData.skills[index].name"
              placeholder="技术名称"
              style="width:60%;"
            />
            <el-slider
              v-model="formData.skills[index].level"
              placeholder="技术水平"
              style="width:60%; margin: auto;"
            />
          </el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item v-for="(contribution,index) in formData.contributions" prop="contributions">
            <span slot="label">
              <el-tooltip content="继续添加开源贡献" placement="left">
                <i class="el-icon-circle-plus-outline" @click="addContribution" />
              </el-tooltip>
              <h2>开源贡献</h2>
              <el-tooltip v-if="index>0" content="删除开源贡献" placement="right">
                <i class="el-icon-remove-outline" @click="removeContribution(index)" />
              </el-tooltip>
            </span>
            <el-input
              v-model="formData.contributions[index].name"
              placeholder="开源名称"
              style="width:60%;"
            />
            <el-input
              v-model="formData.contributions[index].description"
              placeholder="开源描述"
              style="width:60%;padding-top:10px;"
            />
            <el-input
              v-model="formData.contributions[index].url"
              placeholder="开源链接"
              style="width:60%;padding-top:10px;"
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

export default {
  components: { ...mapGetters([
    'username',
    'name'
  ]) },
  props: [],
  data() {
    return {
      formData: {
        stuno: undefined,
        stuname: undefined,
        experience: [
          {
            company: '',
            position: '',
            timeperiod: '',
            description: '',
            website: ''
          }
        ],
        education: [
          {
            degree: '',
            timeperiod: '',
            description: '',
            website: ''
          }
        ],
        projects: [
          {
            name: '',
            platform: '',
            timeperiod: '',
            description: '',
            url: ''
          }
        ],
        skills: [
          {
            name: '',
            level: ''
          }
        ],
        contributions: [
          {
            name: '',
            description: '',
            url: ''
          }
        ]
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
        }]

      },
      degreeOptions: [{
        'label': '学士学位',
        'value': '学士学位'
      }, {
        'label': '硕士学位',
        'value': '硕士学位'
      }, {
        'label': '博士学位',
        'value': '博士学位'
      }]
    }
  },
  created() {
    this.formData.stuname = this.$store.getters.name
    this.formData.stuno = this.$store.getters.username
    this.getList()
  },
  methods: {

    // 添加工作经历
    addExperience() {
      this.formData.experience.push({
        company: '',
        position: '',
        timeperiod: '',
        description: '',
        website: ''
      })
    },
    // 删除工作经历
    removeExperience(index) {
      this.formData.experience.splice(index, 1)
    },
    // 添加教育经历
    addEducation() {
      this.formData.education.push({
        degree: '',
        timeperiod: '',
        description: '',
        website: ''
      })
    },
    // 删除教育经历
    removeEducation(index) {
      this.formData.education.splice(index, 1)
    },
    // 添加项目经历
    addProject() {
      this.formData.projects.push({
        name: '',
        platform: '',
        timeperiod: '',
        description: '',
        url: ''
      })
    },
    // 删除项目经历
    removeProject(index) {
      this.formData.projects.splice(index, 1)
    },

    // 添加技术栈
    addSkill() {
      this.formData.skills.push({
        name: '',
        level: ''
      })
    },

    // 删除技术栈
    removeSkill(index) {
      this.formData.skills.splice(index, 1)
    },

    // 添加开源贡献
    addContribution() {
      this.formData.contributions.push(
        {
          name: '',
          description: '',
          url: ''
        }
      )
    },

    // 删除开源贡献
    removeContribution(index) {
      this.formData.contributions.splice(index, 1)
    },

    // 查询
    getList() {
      listResume(this.formData.stuno).then(response => {
        this.loading = false
        if (response.data == null) {
          this.resetForm()
        } else {
          this.formData = response.data
          //   防止返回Json数组为null
          if (this.formData.education == null) {
            this.restartOtherResume()
            console.log(this.$options.data())
          }
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
    },

    // 初始化Json数组表单数据
    restartOtherResume() {
      this.formData.experience = this.$options.data().formData.experience
      this.formData.education = this.$options.data().formData.education
      this.formData.projects = this.$options.data().formData.projects
      this.formData.skills = this.$options.data().formData.skills
      this.formData.contributions = this.$options.data().formData.contributions
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
    margin-right: 10px;

  }
  .el-icon-remove-outline{
    margin-left: 10px;
  }

  // 样式穿透
::v-deep .el-textarea .el-input__count {
    color: #909399;
    background: transparent !important;
    bottom: -10px;
}

h2{
    display:inline-block;
    color: #606266;
    font-weight:bolder;
}

  </style>

