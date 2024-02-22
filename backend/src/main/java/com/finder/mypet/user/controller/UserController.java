package com.finder.mypet.user.controller;

import com.finder.mypet.jwt.dto.response.JwtResponse;
import com.finder.mypet.user.dto.response.UserJoinSuccessResponse;
import com.finder.mypet.user.dto.request.UserRequest;
import com.finder.mypet.user.dto.request.UserLoginRequest;
import com.finder.mypet.user.dto.response.UserInfoResponse;
import com.finder.mypet.user.dto.response.UserNicknameResponse;
import com.finder.mypet.user.service.UserService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // 회원 가입 [ㅇ]
    @PostMapping("/user/join")
    public ResponseEntity<?> join(@RequestBody UserRequest dto) {
        userService.join(dto.getUserId(), dto.getPassword(), dto.getNickname());
        UserJoinSuccessResponse response = UserJoinSuccessResponse.toDto();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // 로그인 [ㅇ] - access token 발급
    @PostMapping("/user/login")
    public ResponseEntity<?> login(@RequestBody UserLoginRequest dto) {
        JwtResponse token = userService.login(dto.getUserId(), dto.getPassword());

        UserNicknameResponse nickname = userService.getNickname(dto.getUserId());

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Bearer " + token.getAccessToken());

        return new ResponseEntity<>(nickname, httpHeaders, HttpStatus.OK);
    }



    // 회원 정보 조회 [ㅇ]
    @GetMapping("/user/mypage")
    public ResponseEntity<?> getInfo(@AuthenticationPrincipal User user) {
        String userId = user.getUsername();
        UserInfoResponse info = userService.getInfo(userId);
        return new ResponseEntity<>(info, HttpStatus.OK);
    }


    // 회원 정보 수정 [ ] - userId 중복일 때 에러 잡기
    @PutMapping("/user/mypage")
    public ResponseEntity<?> updateInfo(@AuthenticationPrincipal User user, @RequestBody UserRequest dto) {
        String userId = user.getUsername();
        userService.updateInfo(userId, dto.getPassword(), dto.getNickname());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // 회원 탈퇴
    @Transactional  // deleteBy 를 사용하여 직접 조건을 거는 경우에는 직접 붙여줘야 함.
    @DeleteMapping("/user/mypage")
    public ResponseEntity<?> withdraw(@AuthenticationPrincipal User user) {
        String userId = user.getUsername();

        userService.deleteByUserId(userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // 닉네임만 보내주기
//    public ResponseEntity<?> getNickname(@AuthenticationPrincipal User user) {
//        String userId = user.getUsername();
//        UserNicknameResponse nickname = userService.getNickname(userId);
//
//        return new ResponseEntity<>(nickname, HttpStatus.OK);
//    }
}
