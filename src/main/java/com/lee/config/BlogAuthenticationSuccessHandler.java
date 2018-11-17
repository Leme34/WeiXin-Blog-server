package com.lee.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lee.vo.BlogResponseResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * security认证成功后调用此类的onAuthenticationSuccess方法
 */
@Component("blogAuthenticationSuccessHandler")
public class BlogAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Autowired
    private ObjectMapper objectMapper;

    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * security登录成功时会被调用
     * authentication: 登录后的用户信息对象
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        String username = authentication.getName();
        logger.info(username + "登录成功");
        //返回用户信息对象principal给小程序
        response.setContentType("application/json;charset=UTF-8");
        ResponseEntity<BlogResponseResult> responseEntity = ResponseEntity.ok(new BlogResponseResult(200, authentication.getPrincipal()));
        response.getWriter().write(objectMapper.writeValueAsString(responseEntity));
    }
}
