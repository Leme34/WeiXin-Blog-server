package com.lee.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public enum  BlogExceptionEnum {

    ;

    private Integer statusCode;
    private String msg;
}
