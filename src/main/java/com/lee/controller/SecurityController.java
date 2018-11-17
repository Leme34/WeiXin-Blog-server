package com.lee.controller;

import com.lee.vo.BlogResponseResult;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SecurityController {

    /**
     * 按照配置当服务器的session过期时security调用
     * 当前端获取到此返回信息时提示并跳转登录页
     */
    @GetMapping("/session/invalid")
//    @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
    public ResponseEntity sessionInvalid() {
        //认证失败错误状态码401
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new BlogResponseResult(401, "session已过期,请重新登录"));
    }


}
