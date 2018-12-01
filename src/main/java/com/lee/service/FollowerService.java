package com.lee.service;

import com.lee.pojo.Follower;
import com.lee.pojo.User;

import java.util.List;

public interface FollowerService {

    /**
     * 关注用户
     */
    void followerUser(Long userId,Long followerId);

    /**
     * 查询该用户关注的所有用户
     */
    List<Follower> getFollowList(Long followerId);

    void cancelFollowerUser(Long userId,Long followerId);

    boolean isFollower(Long userId,Long followerId);

    /**
     * 查询该用户的粉丝
     */
    List<Follower> getFollowersByUserId(Long userId);

}
