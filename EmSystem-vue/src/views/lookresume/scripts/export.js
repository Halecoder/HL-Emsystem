
// const puppeteer = require('puppeteer')
const fs = require('fs')
const path = require('path')
const http = require('http')
// const config = require('../config')

require('./puppeteerByPool')

const {
  interval
} = require('rxjs')
const {
  filter,
  first,
  mergeMap
} = require('rxjs/operators')

const fetchResponse = () => {
  return new Promise((res, rej) => {
    try {
      const req = http.request(`http://localhost:9528/#/`, response => res(response.statusCode))
      req.on('error', (err) => rej(err))
      req.end()
    } catch (err) {
      rej(err)
    }
  })
}

const waitForServerReachable = () => {
  return interval(1000).pipe(
    mergeMap(async() => {
      try {
        const statusCode = await fetchResponse()
        if (statusCode === 200) return true
      // eslint-disable-next-line no-empty
      } catch (err) {}
      return false
    }),
    filter(ok => !!ok)
  )
}

// const timedOut = timeout => {
//   return new Promise(res => {
//     setTimeout(res, timeout)
//   })
// }

const convert = async() => {
  await waitForServerReachable().pipe(
    first()
  ).toPromise()

  console.log('Connected to server ...')
  console.log('Exporting ...')
  try {
    const fullDirectoryPath = path.join(__dirname, '../pdf/')
    const directories = getResumesFromDirectories()
    const browser = await global.pp.use()
    const pageLogin = await browser.newPage()
    // await pageLogin.setDefaultNavigationTimeout(0)
    await autoLogin(pageLogin)
    await directories.forEach(async(dir) => {
      // const browser = await puppeteer.launch({
      //   headless: true,
      //   args: ['--no-sandbox']
      // })

      const pageWork = await browser.newPage()
      // await pageWork.setDefaultNavigationTimeout(0)

      await pageWork.goto(`http://localhost:9528/#/resumes/` + dir.name, {
        waitUntil: 'load'
        // waitUntil: 'networkidle2' // 在 500ms 内网络连接个数不超过 2 个
      })

      await pageWork.waitFor('.resume')

      if (
        !fs.existsSync(fullDirectoryPath)
      ) {
        fs.mkdirSync(fullDirectoryPath)
      }
      await pageWork.pdf({
        path: fullDirectoryPath + dir.name + '.pdf',
        format: 'A4'
      })
    }

    )
  } catch (err) {
    throw new Error(err)
  }
  console.log('Finished exports.')
}

const autoLogin = async(pageLogin) => {
  await pageLogin.goto('http://localhost:9528/#/login/')
  await pageLogin.waitFor('.login-box') // 等待登陆框出现
  await pageLogin.type('#username', 'admin') // 输入用户名
  await pageLogin.type('#password', '12345678') // 输入密码
  // page.type(".vertify_code", "password", 100);
  // 登陆
  await pageLogin.click('#loginButton')
}

const getResumesFromDirectories = () => {
  const directories = getDirectories()
  return directories
    .map(dir => {
      const fileName = dir.replace('.vue', '')
      return {
        path: fileName,
        name: fileName
      }
    })
}

const getDirectories = () => {
  const srcpath = path.join(__dirname, '../resumes')
  return fs.readdirSync(srcpath)
    .filter(file => file !== 'resumes.js' && file !== 'template.vue' && file !== 'options.js')
}

module.exports = convert()
