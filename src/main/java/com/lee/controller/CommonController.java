package com.lee.controller;

import com.lee.pojo.User;
import com.lee.service.UserService;
import com.lee.service.impl.MailServiceImpl;
import com.lee.vo.BlogResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 负责公共业务处理
 */
@Api(value = "公共接口", tags = {"CommonController"})
@RestController
public class CommonController {
    private final Integer DEFAULT_USER_ROLE = 2;

    //保存当前请求验证码的线程副本
    private ConcurrentHashMap concurrentHashMap = new ConcurrentHashMap();
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private MailServiceImpl mailService;
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private UserService userService;

    @ApiOperation(value = "用户注册", notes = "用户注册")
    @PostMapping("/register")
    public ResponseEntity register(User user, String captcha, HttpServletRequest request) {  //提交的邮箱验证码
        //查询该邮箱是否已被注册,若已被注册直接返回
        if (userService.emailIsRegisted(user.getEmail())) {
            return ResponseEntity.ok(new BlogResponseResult(201, "邮件已被注册~"));
        }

        //取出当前请求生成的验证码
        String geneCaptcha = (String) request.getSession().getAttribute("captcha");

        logger.info("user = " + user + "geneCaptcha = " + geneCaptcha + "  captcha = " + captcha);
        //此session没有获取过验证码 或 验证码错误
        if (captcha == null || !captcha.equals(geneCaptcha)) {
            return ResponseEntity.ok(new BlogResponseResult(201, "验证码错误~"));
        }
        //密码编码
        String encodePwd = encoder.encode(user.getPassword());
        user.setPassword(encodePwd);
        //加上默认角色权限
        user.setUserRole(DEFAULT_USER_ROLE);
        //入库
        userService.saveUser(user);
        return ResponseEntity.ok(new BlogResponseResult(200, "注册成功~"));
    }

    @ApiOperation(value = "获取邮箱验证码", notes = "发送验证码邮件到此邮箱")
    @GetMapping("/sendEmail")
    public ResponseEntity register(String email, HttpServletRequest request) {
        //邮箱不能为空
        if (StringUtils.isBlank(email)) {
            return ResponseEntity.ok(new BlogResponseResult(201, "请输入正确的邮件地址~"));
        }
        //查询该邮箱是否已被注册,若已被注册直接返回
        if (userService.emailIsRegisted(email)) {
            return ResponseEntity.ok(new BlogResponseResult(201, "邮件已被注册~"));
        }

        //发送邮件
        String captcha = mailService.sendMail(email);
        logger.info("生成的验证码为:" + captcha);
        //当前请求生成的验证码放入session,在注册handler取出验证
        request.getSession().setAttribute("captcha", captcha);

        return ResponseEntity.ok(new BlogResponseResult(200, "邮件发送成功~"));
    }

}
