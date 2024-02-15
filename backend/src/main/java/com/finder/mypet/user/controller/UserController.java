package com.finder.mypet.user.controller;

import com.finder.mypet.jwt.dto.response.JwtResponse;
import com.finder.mypet.user.dto.response.JoinSuccessResponse;
import com.finder.mypet.user.dto.request.UserRequest;
import com.finder.mypet.user.dto.request.UserLoginRequest;
import com.finder.mypet.user.dto.response.UserInfoResponse;
import com.finder.mypet.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000", exposedHeaders = "Authorization")
public class UserController {

    private final UserService userService;

    // 회원 가입
    @PostMapping("/user/join")
    public ResponseEntity<?> join(@RequestBody UserRequest dto) {
        userService.join(dto.getUserId(), dto.getPassword(), dto.getNickname());
        JoinSuccessResponse response = JoinSuccessResponse.toDto();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // 로그인 - access token 발급
    @PostMapping("/user/login")
    public ResponseEntity<?> login(@RequestBody UserLoginRequest dto) {
        JwtResponse token = userService.login(dto.getUserId(), dto.getPassword());

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Bearer " + token.getAccessToken());

        return new ResponseEntity<>(httpHeaders, HttpStatus.OK);
    }

    // 회원 정보 조회
    @GetMapping("/user/mypage")
    public ResponseEntity<?> getInfo(@AuthenticationPrincipal User user) {
        String userId = user.getUsername();
        UserInfoResponse info = userService.getInfo(userId);
        return new ResponseEntity<>(info, HttpStatus.OK);
    }

    // 회원 정보 수정
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

    /*
    // ex.인증된 사람만 게시판에 글 쓰기
    @PostMapping("/board/*")
    public ResponseEntity<String> writeReview(Authentication authentication) {
        return ResponseEntity.ok(authentication.getName() + "님의 리뷰 등록이 완료 되었음");
    }
     */

}
