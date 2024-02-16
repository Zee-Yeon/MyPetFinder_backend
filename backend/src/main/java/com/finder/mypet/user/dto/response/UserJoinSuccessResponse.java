package com.finder.mypet.user.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserJoinSuccessResponse {
    private int status;
    private String message;

    public static UserJoinSuccessResponse toDto() {
        return UserJoinSuccessResponse.builder()
                .status(200)
                .message("회원가입 성공")
                .build();
    }
}
