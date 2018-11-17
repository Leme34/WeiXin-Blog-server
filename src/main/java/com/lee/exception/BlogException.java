package com.lee.exception;

import com.lee.enums.BlogExceptionEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BlogException extends RuntimeException{

    private BlogExceptionEnum exceptionEnum;

}
