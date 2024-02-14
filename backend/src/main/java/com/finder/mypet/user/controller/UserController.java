package com.finder.mypet.user.controller;

import com.finder.mypet.jwt.dto.response.JwtResponse;
import com.finder.mypet.user.domain.entity.User;
import com.finder.mypet.user.dto.response.JoinSuccessResponse;
import com.finder.mypet.user.dto.request.UserJoinRequest;
import com.finder.mypet.user.dto.request.UserLoginRequest;
import com.finder.mypet.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    // test
    @GetMapping("/home/{id}")
    public Optional<User> test(@PathVariable Long id) {
        return userService.getMember(id);
    }

    @PostMapping("/user/join")
    public ResponseEntity<?> join(@RequestBody UserJoinRequest dto) {
        userService.join(dto.getUserId(), dto.getPassword(), dto.getNickname());
        JoinSuccessResponse response = JoinSuccessResponse.toDto();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/user/login")
    public ResponseEntity<?> login(@RequestBody UserLoginRequest dto) {
        JwtResponse token = userService.login(dto.getUserId(), dto.getPassword());

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Bearer " + token);

        return new ResponseEntity<>(token, httpHeaders, HttpStatus.OK);
    }


    /*
    // ex.인증된 사람만 게시판에 글 쓰기
    @PostMapping("/board/*")
    public ResponseEntity<String> writeReview(Authentication authentication) {
        return ResponseEntity.ok(authentication.getName() + "님의 리뷰 등록이 완료 되었음");
    }
     */

}
