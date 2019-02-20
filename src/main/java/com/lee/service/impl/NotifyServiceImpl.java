package com.lee.service.impl;

import com.lee.mapper.NotifyMapper;
import com.lee.pojo.Notify;
import com.lee.service.NotifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotifyServiceImpl implements NotifyService {

    @Autowired
    private NotifyMapper notifyMapper;

    @Override
    public void saveNotify(Long user1, Long user2, Integer operationId) {
        Notify notify = new Notify(user1, user2, operationId);
        notifyMapper.insert(notify);
    }



}
