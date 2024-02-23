package com.finder.mypet.common.advice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Builder
public class ExceptionContent {
    private String field;
    private String message;
}
