package com.lee.service.impl;

import com.lee.service.SearchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Service
@Slf4j
public class SearchServiceImpl implements SearchService {
    @Autowired
    private StringRedisTemplate redisTemplate;

    //redis中有序集合的名称(key)
    @Value("${REDIS_ZSET_KEY}")
    private String REDIS_ZSET_KEY;
    //记录每一个key的存入时间
    @Value("${REDIS_TIME_KEY_PREFIX}")
    private String REDIS_TIME_KEY_PREFIX;

    /**
     * 若第一次查询该key则缓存到ZSet中
     * 否则获取排序值并且+1，记录存入/更新存入时间
     */
    @Override
    public void searchKey(String key) {
        ZSetOperations zSetOperations = redisTemplate.opsForZSet();
        ValueOperations timeOperations = redisTemplate.opsForValue();
        //获得有序集合中key元素的索引index
        Long index = zSetOperations.rank(REDIS_ZSET_KEY, key);
        //如果index=null,则缓存里面不存在该key
        if (null == index) {
            //添加缓存,默认排序值=1
            zSetOperations.add(REDIS_ZSET_KEY, key, 1.0);
            log.info("当前:" + key + ":的搜索次数为1");
        } else {
            // 如果key已存在,则获取排序值并且+1
            Integer score = zSetOperations.score(REDIS_ZSET_KEY, key).intValue();
            zSetOperations.incrementScore(REDIS_ZSET_KEY, key, 1);
            log.info("当前:" + key + ":的搜索次数为" + (score + 1));
        }
        //当前时间
        Long now = System.currentTimeMillis();
        //记录存入/更新存入时间
        timeOperations.getAndSet(REDIS_TIME_KEY_PREFIX + ":" + key, now+"");
    }

    /**
     * 获取redis中的前num名热搜词,遍历排序集把时间超过3天没搜索的词热度归0，防止霸屏现象
     */
    @Override
    public List getHotSearchRecords(Integer num) {
        List<String> resultList = new ArrayList<>();
        ValueOperations timeOperations = redisTemplate.opsForValue();
        Long now = System.currentTimeMillis();

        //从高到低的排序集中获取从头(start)到尾(end)内的元素。
        ZSetOperations zSetOperations = redisTemplate.opsForZSet();
        Set<String> keySet = zSetOperations.reverseRange(REDIS_ZSET_KEY, 0, Long.MAX_VALUE);  //查询所有key
        if (keySet != null && keySet.size() != 0) {
            //遍历排序集
            for (String key : keySet) {
                //只取出最近3天搜索量前十名
                if (resultList.size() > 9) {
                    break;
                }
                //获取此key的存入时间，若超过3天没搜索不加入结果集并热度归0
                Long setTime = Long.parseLong((String) timeOperations.get(REDIS_TIME_KEY_PREFIX + ":" + key));
                setTime = setTime == null ? 0L : setTime;  //避免空指针
                if (now - setTime < 86400000 * 3L) {  //1天86400秒
                    resultList.add(key);
                } else {
                    log.info(key + "  超过3天没搜索热度归0!");
                    zSetOperations.add(REDIS_ZSET_KEY, key, 0);
                }
            }
        }
        return resultList;
    }

}
