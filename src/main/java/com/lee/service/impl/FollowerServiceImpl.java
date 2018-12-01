package com.lee.service.impl;

import com.lee.mapper.FollowerMapper;
import com.lee.pojo.Follower;
import com.lee.pojo.User;
import com.lee.service.FollowerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FollowerServiceImpl implements FollowerService {

    @Autowired
    private FollowerMapper followerMapper;

    @Override
    public void followerUser(Long userId, Long followerId) {
        Follower follower = new Follower();
        follower.setUserId(userId);
        follower.setFollowerId(followerId);
        followerMapper.insert(follower);
    }

    @Override
    public List<Follower> getFollowList(Long followerId) {
        return followerMapper.getFollowListByFollowerId(followerId);
    }

    @Override
    public boolean isFollower(Long userId, Long followerId) {
        Follower follower = new Follower();
        follower.setUserId(userId);
        follower.setFollowerId(followerId);
        return followerMapper.selectOne(follower) == null ? false : true;
    }

    @Override
    public void cancelFollowerUser(Long userId, Long followerId) {
        Follower follower = new Follower();
        follower.setUserId(userId);
        follower.setFollowerId(followerId);
        followerMapper.delete(follower);
    }

    @Override
    public List<Follower> getFollowersByUserId(Long userId) {
        return followerMapper.getFollowersByUserId(userId);
    }
}
