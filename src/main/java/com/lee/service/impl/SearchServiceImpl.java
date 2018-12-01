package com.lee.service.impl;

import com.lee.mapper.BlogMapper;
import com.lee.pojo.Blog;
import com.lee.service.SearchService;
import com.lee.vo.BlogVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import tk.mybatis.mapper.entity.Example;

import javax.xml.stream.events.StartElement;
import java.util.List;
import java.util.Set;

@Service
@Slf4j
public class SearchServiceImpl implements SearchService {
    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private BlogMapper blogMapper;

    //redis中有序集合的名称(key)
    @Value("${REDIS_ZSET_KEY}")
    private String REDIS_ZSET_KEY;

    /**
     * 若第一次查询该key则缓存到ZSet中
     * 否则获取排序值并且+1
     */
    @Override
    public void searchKey(String key) {
        //获得有序集合中key元素的索引
        ZSetOperations zSetOperations = redisTemplate.opsForZSet();
        Long index = zSetOperations.rank(REDIS_ZSET_KEY, key);
        //如果index=null,则缓存里面不存在该key
        if (null == index){
            //添加缓存,默认排序值=1
            zSetOperations.add(REDIS_ZSET_KEY,key,1.0);
            log.info("当前:"+key +":的搜索次数为1");
        }else {
            // 如果key已存在,则获取排序值并且+1
            int score = zSetOperations.score(REDIS_ZSET_KEY, key).intValue();
            zSetOperations.add(REDIS_ZSET_KEY,key,score+1);
            log.info("当前:"+key +":的搜索次数为"+(score+1));
        }
    }

    /**
     * 查询redis中的前num名热搜词
     */
    @Override
    public Set getHotSearchRecords(Integer num) {
        //从高到低的排序集中获取从头(start)到尾(end)内的元素。
        ZSetOperations zSetOperations = redisTemplate.opsForZSet();
        Set resultSet = zSetOperations.reverseRange(REDIS_ZSET_KEY, 0, num);
        return resultSet;
    }

}
