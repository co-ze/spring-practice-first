package com.hanghae.springlevelone.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExceptionResponseDto {
    private String message;
    private int statusCode;
}
