package com.lee.vo;

import com.lee.enums.BlogExceptionEnum;
import com.lee.pojo.Blog;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 请求响应体的vo对象
 * 放入ResponseEntity中的body中返回前端
 */
@NoArgsConstructor
@Data
public class BlogResponseResult {

    private Integer statusCode;
    private Object data;

    //返回信息时使用的构造方法
    public BlogResponseResult(Integer statusCode,Object data){
        this.statusCode = statusCode;
        this.data = data;
    }

    //返回错误信息时使用的构造方法
    public BlogResponseResult(BlogExceptionEnum em){
        statusCode = em.getStatusCode();
        data = em.getMsg();
    }

}
