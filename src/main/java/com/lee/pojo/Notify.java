package com.lee.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户通知实体类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Notify {
    private Long user1;  //主动方
    private Long user2;  //被动方
    private Integer operationId; //操作标识  0:关注了你; 1:赞了你的博客; 2:收藏了你的博客
    private Integer isNew;  //是否未读,默认true
    private Long blogId;  //若不涉及blog的操作可为null

    public Notify(Long user1, Long user2, Integer operationId) {
        this.user1 = user1;
        this.user2 = user2;
        this.operationId = operationId;
    }
}

