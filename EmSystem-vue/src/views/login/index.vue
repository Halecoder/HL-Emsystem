<template>
  <div id="app">
    <el-form ref="loginForm" label-width="80px" :rules="rules" :model="form" class="login-box">
      <h3 class="login-title">就业管理系统</h3>
      <el-form-item label="账号" prop="username">
        <el-input v-model="form.username" type="text" placeholder="请输入账号" />
      </el-form-item>
      <el-form-item label="密码" prop="password">
        <el-input v-model="form.password" type="password" placeholder="请输入密码" />
      </el-form-item>
      <el-form-item label="验证码" prop="verificationCode">
        <el-input
          v-model="form.verificationCode"
          type="text"
          placeholder="点击图片更换验证码"
          class="vertify_code"
          auto-complete="false"
        />
        <img id="imgVerifyCode" src="/api/verify_code" @click="reloadVerifyCode">
      </el-form-item>
      <el-form-item>
        <el-button type="primary" style="width:200px" @click="onSubmit('loginForm')">登录</el-button>
      </el-form-item>

    </el-form>

    <!--  底部  -->
    <div class="el-login-footer">
      <span>Copyright © 2022-2023 HL All Rights Reserved.</span>
    </div>
  </div>
</template>

<script >
// import { validUsername } from '@/utils/validate'

export default {
  name: 'Login',
  data() {
    // const validateUsername = this.form.username
    //   if (!validUsername(value)) {
    //     callback(new Error('请输入正确的用户名'))
    //   } else {
    //     callback()
    //   }
    // }
    const validatePassword = (rule, value, callback) => {
      if (value.length < 8) {
        callback(new Error('密码不能少于8位'))
      } else {
        callback()
      }
    }
    const validateCode = (rule, value, callback) => {
      if (value.length < 4) {
        callback(new Error('验证码不能少于4位'))
      } else {
        callback()
      }
    }
    return {
      form: {
        username: 'admin',
        password: '12345678',
        verificationCode: ''
      },
      rules: {
        // username: [{ required: true, trigger: 'blur', validator: validateUsername }],
        password: [{ required: true, trigger: 'blur', validator: validatePassword }],
        verificationCode: [{ required: true, trigger: 'blur', validator: validateCode }]
      }
    }
  },
  methods: {
    reloadVerifyCode() {
      document.getElementById('imgVerifyCode').src = '/api/verify_code?time=' + new Date().getTime()
    },
    onSubmit(formName) {
      this.$refs[formName].validate(valid => {
        if (valid) {
          this.loading = true
          this.$store.dispatch('user/login', this.form).then(() => {
            this.$router.push({ path: '/' })
            this.$router.go(0)
          }).catch(() => {
            this.loading = false
            this.reloadVerifyCode()
          })
        } else {
          return false
        }
      })
    }
  }
}
</script>

<style rel="stylesheet/scss" lang="scss">

        #app{
          display: flex;
          justify-content: center;
          align-items: center;
          height: 100%;
          background-image: url("../../assets/images/login-background.jpg");
          background-size: cover;
          position: relative;

        }
        .login-box {
            position: absolute;
            background: #ffffff;
            /* border: 1px solid #DCDFE6; */
            width: 400px;
            /* margin: 180px auto; */
            padding: 35px 35px 15px 35px;
            border-radius: 5px;
            -webkit-border-radius: 5px;
            -moz-border-radius: 5px;
            /* box-shadow: 0 0 25px #909399; */
        }
        .login-title{
            text-align: center;
            margin: 0 auto 40px auto;
            color: #303133;
        }
        #imgVerifyCode {
            position: absolute;
            right: 0;
            bottom: 0;
            width: 80px;
        }
        .vertify_code {
            width: 160px;
        }
        .el-login-footer {
            height: 40px;
            line-height: 40px;
            position: fixed;
            bottom: 0;
            width: 100%;
            text-align: center;
            color: #fff;
            font-family: Arial;
            font-size: 12px;
            letter-spacing: 1px;
        }
    </style>
