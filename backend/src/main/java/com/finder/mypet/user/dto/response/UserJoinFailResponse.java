package com.finder.mypet.user.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserJoinFailResponse {
        private int status;
        private String message;

    public static UserJoinFailResponse toDto() {
        return UserJoinFailResponse.builder()
                .status(400)
                .message("이미 존재하는 회원입니다.")
                .build();
    }
}
