package com.lee.mapper;

import com.lee.pojo.BlogVote;
import com.lee.pojo.Follower;
import com.lee.utils.MyMapper;

import java.util.List;

public interface FollowerMapper extends MyMapper<Follower> {

    /**
     * 查询该用户关注的所有用户
     */
    List<Follower> getFollowListByFollowerId(Long followerId);

    /**
     * 查询该用户的粉丝
     */
    List<Follower> getFollowersByUserId(Long userId);

}