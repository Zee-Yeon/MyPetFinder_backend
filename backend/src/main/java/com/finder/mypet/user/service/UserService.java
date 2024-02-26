package com.finder.mypet.user.service;

import com.finder.mypet.common.advice.exception.CustomException;
import com.finder.mypet.common.response.ResponseCode;
import com.finder.mypet.jwt.dto.response.JwtResponse;
import com.finder.mypet.user.domain.entity.User;
import com.finder.mypet.user.domain.repository.UserRepository;
import com.finder.mypet.jwt.util.JwtProvider;
import com.finder.mypet.user.dto.request.UserRequest;
import com.finder.mypet.user.dto.response.UserInfoResponse;
import com.finder.mypet.user.dto.response.UserNicknameResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtProvider jwtProvider;

    @Transactional
    public void join(UserRequest dto) {
        userExists(dto.getUserId());
        nicknameExists(dto.getNickname());

        User user = User.builder()
                .userId(dto.getUserId())
                .password(encoder.encode(dto.getPassword()))
                .nickname(dto.getNickname())
                .build();
        userRepository.save(user);
    }

    // access token 발급
    public JwtResponse login(String userId, String password) {
        // 1. Login ID/PW 를 기반으로 Authentication 객체 생성
        // 이때 authentication 는 인증 여부를 확인하는 authenticated 값이 false
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userId, password);
        // 2. 실제 검증 (사용자 비밀번호 체크)이 이루어지는 부분
        // authenticate 매서드가 실행될 때 CustomUserDetailsService 에서 만든 loadUserByUsername 메서드가 실행
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        // 3. 인증 정보를 기반으로 JWT 토큰 생성
        JwtResponse token = jwtProvider.createToken(authentication);

        return token;
    }

    @Transactional(readOnly = true)
    public UserInfoResponse getInfo(org.springframework.security.core.userdetails.User userDetail) {
        String userId = userDetail(userDetail);
        User user = findByUserId(userId);
        return new UserInfoResponse(user.getUserId(), user.getNickname());
    }

    @Transactional
    public void updateInfo(org.springframework.security.core.userdetails.User userDetail, UserRequest dto) {
        String userId = userDetail(userDetail);
        User user = findByUserId(userId);

        nicknameExists(dto.getNickname());
        user.modifyNickname(dto.getNickname());

        userRepository.save(user);
    }

    @Transactional
    public void deleteByUserId(org.springframework.security.core.userdetails.User userDetail) {
        String userId = userDetail(userDetail);
        findByUserId(userId);
        userRepository.deleteByUserId(userId);
    }

    public void userExists(String userId) {
        userRepository.findByUserId(userId)
                .ifPresent(user -> {
                    throw new CustomException(ResponseCode.USER_ALREADY_EXIST);
                });
    }

    public void nicknameExists(String nickname) {
        userRepository.findByNickname(nickname)
                .ifPresent(user -> {
                    throw new CustomException(ResponseCode.NICKNAME_ALREADY_EXIST);
                });
    }

    public String userDetail(org.springframework.security.core.userdetails.User user) {
        return user.getUsername();
    }

    public User findByUserId(String userId) {
        return userRepository.findByUserId(userId)
                .orElseThrow(() -> new CustomException(ResponseCode.NOT_FOUND_USER));
    }

    public UserNicknameResponse getNickname(String userId) {
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new CustomException(ResponseCode.NOT_FOUND_USER));

        return new UserNicknameResponse(user.getNickname());
    }
}
