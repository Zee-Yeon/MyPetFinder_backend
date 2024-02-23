package com.finder.mypet.user.controller;

import com.finder.mypet.common.response.Response;
import com.finder.mypet.common.response.ResponseCode;
import com.finder.mypet.jwt.dto.response.JwtResponse;
import com.finder.mypet.user.dto.request.UserRequest;
import com.finder.mypet.user.dto.request.UserLoginRequest;
import com.finder.mypet.user.dto.response.UserInfoResponse;
import com.finder.mypet.user.dto.response.UserNicknameResponse;
import com.finder.mypet.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import static com.finder.mypet.common.response.ResponseCode.*;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // 회원 가입 [ㅇ]
    @PostMapping("/user/join")
    public ResponseEntity<?> join(@RequestBody UserRequest dto) {
        userService.join(dto.getUserId(), dto.getPassword(), dto.getNickname());
        return new ResponseEntity<>(Response.create(SUCCESS_SIGNUP, null), SUCCESS_SIGNUP.getHttpStatus());
    }

    // 로그인 [△]
    @PostMapping("/user/login")
    public ResponseEntity<?> login(@RequestBody UserLoginRequest dto) {
        JwtResponse token = userService.login(dto.getUserId(), dto.getPassword());

        UserNicknameResponse nickname = userService.getNickname(dto.getUserId());

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Bearer " + token.getAccessToken());

        return new ResponseEntity<>(nickname, httpHeaders, SUCCESS_LOGIN.getHttpStatus());
    }

    // 회원 정보 조회 [△]
    @GetMapping("/user/mypage")
    public ResponseEntity<?> getInfo(@AuthenticationPrincipal User user) {
        String userId = user.getUsername();
        UserInfoResponse info = userService.getInfo(userId);
        return new ResponseEntity<>(Response.create(SEARCH_MYPAGE, info), SEARCH_MYPAGE.getHttpStatus());
    }


    // 회원 정보 수정 [ㅇ] - userId 중복일 때 에러 잡기
    @PutMapping("/user/mypage")
    public ResponseEntity<?> updateInfo(@AuthenticationPrincipal User user, @RequestBody UserRequest dto) {
        String userId = user.getUsername();
        userService.updateInfo(userId, dto.getPassword(), dto.getNickname());
        return new ResponseEntity<>(Response.create(SUCCESS_EDIT, null), SUCCESS_EDIT.getHttpStatus());
    }

    // 회원 탈퇴 [ㅇ]
    @DeleteMapping("/user/mypage")
    public ResponseEntity<?> withdraw(@AuthenticationPrincipal User user) {
        String userId = user.getUsername();

        userService.deleteByUserId(userId);
        return new ResponseEntity<>(Response.create(SUCCESS_DELETE_USER, null), SUCCESS_DELETE_USER.getHttpStatus());
    }
}
