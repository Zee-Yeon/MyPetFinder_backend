package com.finder.mypet.common.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ResponseCode {

    // USER_SUCCESS
    SUCCESS_SIGNUP(successCode(), HttpStatus.OK, "회원가입이 성공적으로 완료되었습니다."),
    SUCCESS_LOGIN(successCode(), HttpStatus.OK, "로그인이 성공적으로 완료되었습니다."),
    SEARCH_MYPAGE(successCode(), HttpStatus.OK, "회원 정보 조회가 성공적으로 조회되었습니다.."),
    SUCCESS_EDIT(successCode(), HttpStatus.OK, "해당 정보의 수정이 성공적으로 완료되었습니다."),
    SUCCESS_DELETE_USER(successCode(), HttpStatus.OK, "회원 탈퇴가 성공적으로 완료되었습니다."),
    SUCCESS_LOGOUT(successCode(), HttpStatus.OK, "로그아웃이 성공적으로 완료되었습니다."),

    // REVIEW_SUCCESS
    CREATE_REVIEW(successCode(), HttpStatus.OK, "리뷰 작성이 완료되었습니다."),
    GET_REVIEW(successCode(), HttpStatus.OK, "리뷰 상세보기가 성공적으로 조회되었습니다."),
    GET_REVIEWS(successCode(), HttpStatus.OK, "리뷰 전체보기가 성공적으로 조회되었습니다."),
    EDIT_REVIEW(successCode(), HttpStatus.OK, "리뷰 수정이 성공적으로 완료되었습니다."),
    DELETE_REVIEW(successCode(), HttpStatus.OK, "리뷰가 성공적으로 삭제되었습니다."),

    // BOARD_SUCCESS
    CREATE_BOARD(successCode(), HttpStatus.OK, "게시물 작성이 완료되었습니다."),
    GET_BOARD(successCode(), HttpStatus.OK, "게시물 상세보기가 성공적으로 조회되었습니다."),
    GET_BOARDS(successCode(), HttpStatus.OK, "게시물 전체보기가 성공적으로 조회되었습니다."),
    EDIT_BOARDS(successCode(), HttpStatus.OK, "게시물 수정이 성공적으로 완료되었습니다."),
    DELETE_BOARD(successCode(), HttpStatus.OK, "게시물이 성공적으로 삭제되었습니다."),

    // COMMENT_SUCCESS
    CREATE_COMMENT(successCode(), HttpStatus.OK, "댓글 작성이 완료되었습니다."),
    EDIT_COMMENT(successCode(), HttpStatus.OK, "댓글 수정이 성공적으로 완료되었습니다."),
    DELETE_COMMENT(successCode(), HttpStatus.OK, "댓글이 성공적으로 삭제되었습니다."),

    // 400
    BAD_REQUEST(400, HttpStatus.BAD_REQUEST, "잘못된 요청입니다."),

    // 401 인증 x
    NOT_INVALID_JWT(401, HttpStatus.UNAUTHORIZED, "올바르지 않은 JWT 입니다."),
    EXPIRED_JWT(401, HttpStatus.UNAUTHORIZED, "만료된 JWT 입니다."),
    UNSUPPORTED_JWT(401, HttpStatus.UNAUTHORIZED, "지원하지 않는 JWT 입니다."),
    ILLEGAL_JWT(401, HttpStatus.UNAUTHORIZED, "JWT 토큰이 잘못 됐습니다."),
    NOT_AUTHORITY(401, HttpStatus.UNAUTHORIZED, "해당 권한이 존재하지 않습니다."),


    // 404 NOT_FOUND (찾을 수 없는 리소스)
    NOT_FOUND_USER(404, HttpStatus.NOT_FOUND, "존재하지 않는 회원 정보입니다."),
    NOT_FOUND_PASSWORD(404, HttpStatus.NOT_FOUND, "비밀번호가 존재하지 않습니다."),

    NOT_FOUND_REVIEW(404, HttpStatus.NOT_FOUND, "해당 리뷰가 존재하지 않습니다."),

    NOT_FOUND_BOARD(404, HttpStatus.NOT_FOUND, "해당 게시물이 존재하지 않습니다."),

    NOT_FOUND_COMMENT(404, HttpStatus.NOT_FOUND, "해당 댓글이 존재하지 않습니다."),

    // 409 (CONFLICT 중복된 리소스)
    USER_ALREADY_EXIST(409, HttpStatus.CONFLICT, "이미 존재하는 회원 정보입니다."),
    NICKNAME_ALREADY_EXIST(409, HttpStatus.CONFLICT, "이미 존재하는 닉네임입니다.");

    private int code;
    private HttpStatus httpStatus;
    private String message;

    private static int successCode() {
        return 200;
    }
}
