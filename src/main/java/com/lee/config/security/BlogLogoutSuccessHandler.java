package com.lee.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lee.vo.BlogResponseResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component("blogLogoutSuccessHandler")
public class BlogLogoutSuccessHandler implements LogoutSuccessHandler{

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * 登出成功后调用
     */
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        logger.info("authentication="+authentication +"      退出成功");
        ResponseEntity<BlogResponseResult> responseEntity = ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new BlogResponseResult(200, "注销成功~"));
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(responseEntity));

    }

}
