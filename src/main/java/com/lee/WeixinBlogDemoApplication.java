package com.lee;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan("com.lee.mapper")
public class WeixinBlogDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(WeixinBlogDemoApplication.class, args);
	}
}
