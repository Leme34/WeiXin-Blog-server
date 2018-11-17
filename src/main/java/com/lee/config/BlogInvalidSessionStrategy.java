package com.lee.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lee.vo.BlogResponseResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.session.InvalidSessionStrategy;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component("blogInvalidSessionStrategy")
public class BlogInvalidSessionStrategy implements InvalidSessionStrategy {

    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private ObjectMapper objectMapper;

    /**
     * 当session失效时调用
     * 可以使用response.sendRedirect(url)跳转页面
     * 此处使用response的writer返回json数据通知小程序删除过期的cookie再重新发请求或跳转登录页
     */
    @Override
    public void onInvalidSessionDetected(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        logger.info("BlogInvalidSessionStrategy: session失效");
        ResponseEntity<BlogResponseResult> responseEntity = ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new BlogResponseResult(401, "session已过期,请重新登录"));
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(responseEntity));
    }
}
