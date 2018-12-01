package com.lee.config.mq;

import com.lee.pojo.es.EsBlog;
import com.lee.service.BlogService;
import com.lee.service.EsBlogService;
import com.lee.vo.BlogVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

/**
 * 消息队列监听者
 */
@Slf4j
@Component
public class MsgListener {

    @Autowired
    private BlogService blogService;
    @Autowired
    private EsBlogService esBlogService;

    @JmsListener(destination = "blog.topic")
    public void receiveMsg(String message) {
        log.info("接收到消息：blogId=" + message);
        BlogVo blogVo = blogService.queryBlogVoById(new Long(message));
        log.info("查出的blogVo="+blogVo);
        EsBlog esBlog = new EsBlog(blogVo);
        log.info("new EsBlog(blogVo)="+esBlog);
        //添加/更新索引
        esBlogService.updateEsBlog(esBlog);
    }

}
