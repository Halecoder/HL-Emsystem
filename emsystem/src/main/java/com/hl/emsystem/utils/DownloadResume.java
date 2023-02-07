package com.hl.emsystem.utils;

import com.hl.emsystem.common.Constant;
import com.ruiyun.jvppeteer.core.Puppeteer;
import com.ruiyun.jvppeteer.core.browser.Browser;
import com.ruiyun.jvppeteer.core.browser.BrowserFetcher;
import com.ruiyun.jvppeteer.core.page.Page;
import com.ruiyun.jvppeteer.options.*;

import java.util.ArrayList;
import java.util.concurrent.*;

public class DownloadResume {

    public Browser autoLogin() throws Exception {
        //自动下载，第一次下载后不会再下载
        BrowserFetcher.downloadIfNotExist(null);
        ArrayList<String> arrayList = new ArrayList<>();
        LaunchOptions options = new LaunchOptionsBuilder().withArgs(arrayList).withHeadless(true).withDevtools(false).build();
//        页面全屏,不能截图
//    options.setViewport(null);
//                options.setUserDataDir("");
        arrayList.add("--no-sandbox");
        arrayList.add("--disable-setuid-sandbox");
        Browser browser = Puppeteer.launch(options);
        //        登陆模拟
            Page page = browser.newPage();
            String url = Constant.URL + "/#/login";
            page.goTo(url);
            //等待元素加载完成，输入账号密码并提交
            page.waitForSelector(".login-box");
            page.type("#username", "admin");
            Thread.sleep(1000);
            page.type("#password", "12345678");
            Thread.sleep(1000);
            page.click("#loginButton");
            Thread.sleep(2 * 1000);

            return browser;

    }

   public void DownloadImages(String[] resumeNames) throws Exception  {
//        首先登陆
        Browser browser = autoLogin();
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

            for(String resumeName: resumeNames) {
                Page page2 = null;
                try {
                    page2 = pages.take();
                    page2.goTo(Constant.URL + "/#/resumes/" + resumeName,false);

                    page2.waitForSelector(".resume");
                    page2.content();
                    ScreenshotOptions screenshotOptions = new ScreenshotOptions();
                    screenshotOptions.setPath(".\\EmSystem-vue\\src\\views\\lookresume\\images\\" + resumeName + ".png");
                    screenshotOptions.setType("png");
                    screenshotOptions.setFullPage(Boolean.TRUE);
                    page2.screenshot(screenshotOptions);
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
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

    public void DownloadPdf(String resumeName) throws Exception{
//        首先登陆
        Browser browser = autoLogin();

        Page page = browser.newPage();
        page.goTo(Constant.URL + "/#/resumes/" + resumeName);
        page.waitForSelector(".resume");
//        等待数据渲染完成
        page.content();
        // 打印pdf,必须是withHeadless(true)
        PDFOptions pdfOptions = new PDFOptions();
        pdfOptions.setPath(".\\EmSystem-vue\\src\\views\\lookresume\\pdf\\" + resumeName + ".pdf");
        pdfOptions.setFormat("A4");
        page.pdf(pdfOptions);
        page.close();
        browser.close();

    }

}
