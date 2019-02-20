package com.lee.enums;

import lombok.*;

/**
 * 通知实体类存储的操作
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
public enum OperationEnum {

    FOLLOW(0,"关注了你"),
    ACCLAIM(1,"赞了你的博客"),
    MARK(2,"收藏了你的博客")
    ;

    Integer operationId;
    String operation;

}
