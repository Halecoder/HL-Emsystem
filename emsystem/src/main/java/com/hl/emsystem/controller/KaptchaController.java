package com.hl.emsystem.controller;

import com.google.code.kaptcha.Producer;
import com.hl.emsystem.common.ApiRestResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * 验证码生成
 */
@RestController
@RequestMapping("/api")
@Tag(name = "验证码生成管理")
public class KaptchaController {
    @Resource
    private Producer kaptchaProducer;
    @GetMapping("/verify_code")
    public ApiRestResponse createVerifyCode(HttpServletRequest request , HttpServletResponse response) throws IOException {
        //响应立即过期
        response.setDateHeader("Expires" , 0);
        //不缓存任何图片数据
        response.setHeader("Cache-Control" , "no-store,no-cache,must-revalidate");
        response.setHeader("Cache-Control" ,"post-check=0,pre-check=0");
        response.setHeader("Pragma" , "no-cache");
        response.setContentType("image/png");
        //生成验证码图片
        String text = kaptchaProducer.createText();
        request.getSession().setAttribute("kaptchaVerifyCode" , text);
        BufferedImage image = kaptchaProducer.createImage(text);
        ServletOutputStream outputStream = response.getOutputStream();
        ImageIO.write(image, "png", outputStream);
        outputStream.flush();
        outputStream.close();
        return ApiRestResponse.success("验证码生成："+ image);
    }
}
