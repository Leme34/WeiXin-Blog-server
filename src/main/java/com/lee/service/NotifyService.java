package com.lee.service;

public interface NotifyService {

    /**
     * 新增通知
     */
    void saveNotify(Long user1, Long user2,Integer operationId);

}
