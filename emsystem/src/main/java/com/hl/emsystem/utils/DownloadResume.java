package com.hl.emsystem.utils;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTUtil;
import com.hl.emsystem.common.Constant;
import com.ruiyun.jvppeteer.core.Puppeteer;
import com.ruiyun.jvppeteer.core.browser.Browser;
import com.ruiyun.jvppeteer.core.browser.BrowserFetcher;
import com.ruiyun.jvppeteer.core.page.Page;
import com.ruiyun.jvppeteer.options.LaunchOptions;
import com.ruiyun.jvppeteer.options.LaunchOptionsBuilder;
import com.ruiyun.jvppeteer.options.PDFOptions;
import com.ruiyun.jvppeteer.options.ScreenshotOptions;
import com.ruiyun.jvppeteer.protocol.network.CookieParam;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;


@Component
public class DownloadResume {


    /**
     * 实现网页登陆
     *
     * @return
     * @throws Exception
     */
    public Browser autoLogin(HttpServletRequest request) throws Exception {

        //自动下载，第一次下载后不会再下载
        BrowserFetcher.downloadIfNotExist(null);
        ArrayList<String> arrayList = new ArrayList<>();
        LaunchOptions options = new LaunchOptionsBuilder().withArgs(arrayList).withHeadless(true).withDevtools(false).build();
//        页面全屏,不能截图
//    options.setViewport(null);
//                options.setUserDataDir("");
//        options.setTimeout(0);
        arrayList.add("--no-sandbox");
        arrayList.add("--disable-setuid-sandbox");
        Browser browser = Puppeteer.launch(options);
        //        登陆模拟
        Page page = browser.newPage();

        //       token保存在cookie里，把cookie全部赋值给jvppteer即可
//       System.out.println(request.getCookies());
        List<CookieParam> pageCookies = new ArrayList<>();
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            CookieParam cookieParam = new CookieParam();
            cookieParam.setName(cookie.getName());
            cookieParam.setValue(cookie.getValue());
            pageCookies.add(cookieParam);
        }
        String url = Constant.URL + "/#/login";
        page.goTo(url);
        page.setCookie(pageCookies);
        Thread.sleep(2 * 1000);
        return browser;

    }

    /**
     * 批量下载图片
     *
     * @param resumeNames
     * @throws Exception
     */
    public void DownloadImages(String[] resumeNames, Browser browser,  String fileName) throws Exception {
        //启动一个线程池多线程抓取
        int threadCount = 5;
        ThreadPoolExecutor executor = new ThreadPoolExecutor(threadCount, threadCount, 30, TimeUnit.SECONDS, new LinkedBlockingDeque<>());
        CompletionService service = new ExecutorCompletionService(executor);
        //打开5个页面同时抓取，这些页面可以多次利用，这样减少创建网页带来的性能消耗
        LinkedBlockingQueue<Page> pages = new LinkedBlockingQueue<>();
        for (int i = 0; i < threadCount; i++) {
            Page page2 = browser.newPage();
            pages.put(page2);//往队列后面放,阻塞
        }
        Page page2 = null;
        page2 = pages.take();
        for (String resumeName : resumeNames) {

            try {
                page2.goTo(Constant.URL + "/#/resumes/" + resumeName, false);

                page2.waitForSelector(".resume");
                page2.content();
                ScreenshotOptions screenshotOptions = new ScreenshotOptions();
                //必须是绝对路径，要不然会创建到target里
               File file =  FileUtils.touch(Constant.FILE_PATH +"\\images\\"+ fileName+ "\\"+ resumeName + ".png");
               String path =  FileUtils.getAbsolutePath(file);

                screenshotOptions.setPath(path);
                screenshotOptions.setType("png");
                screenshotOptions.setFullPage(Boolean.TRUE);
                page2.screenshot(screenshotOptions);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (page2 != null) {
                    try {
                        pages.put(page2);//把已经抓取完的网页放回队列里
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        browser.close();
    }

    public void DownloadPdf(String resumeName, Browser browser, String fileName) throws Exception {
        //必须是绝对路径，要不然会创建到target里
        File file =  FileUtils.touch(Constant.FILE_PATH +"\\pdf\\"+ fileName+ "\\"+ resumeName + ".pdf");
        String path =  FileUtils.getAbsolutePath(file);
        Page page = browser.newPage();
        page.goTo(Constant.URL + "/#/resumes/" + resumeName);
        page.waitForSelector(".resume");
//        等待数据渲染完成
        page.content();
        // 打印pdf,必须是withHeadless(true)
        PDFOptions pdfOptions = new PDFOptions();
        pdfOptions.setPath(path);
        pdfOptions.setFormat("A4");
        page.pdf(pdfOptions);
        page.close();
        browser.close();

    }

    public String getStuName (HttpServletRequest request){
        String header = request.getHeader("Authorization");
        String token = StringUtils.substringAfter(header, "Bearer ");
         JWT jwt = JWTUtil.parseToken(token);

        //payload
        JSONObject payloads = jwt.getPayloads();
//        Console.log(payloads);
       String username = (String) payloads.get("username");

     return username;

    }

    /**
     * 前端下载pdf文件
     * @param resumeName
     * @param fileName
     * @param response
     * @throws IOException
     */
    public void pdfExportStream(String resumeName,String fileName,HttpServletResponse response) throws IOException {
        BufferedInputStream bufferedInputStream = null;
        String path = Constant.FILE_PATH +"\\pdf\\"+ fileName+ "\\"+ resumeName + ".pdf";
        File file = FileUtil.file(path);
        if(FileUtils.exist(path)){
            bufferedInputStream = FileUtil.getInputStream(file);
            FileUtils.setAttachmentResponseHeader(response,resumeName);
            response.setContentType("text/html;charset=utf-8");
            response.setContentType("application/x-download;charset=utf-8");
            OutputStream out = response.getOutputStream();
            IoUtil.copy( bufferedInputStream,out);

        }
    }

}
