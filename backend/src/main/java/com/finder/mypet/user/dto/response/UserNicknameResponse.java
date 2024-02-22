package com.finder.mypet.user.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.userdetails.User;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserNicknameResponse {
    private String nickname;

    public UserNicknameResponse dto(User user) {
        return UserNicknameResponse.builder()
                .nickname(user.getUsername())
                .build();
    }
}
