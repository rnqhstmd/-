package org.mjulikelion.week3assignment.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mjulikelion.week3assignment.authentication.AuthenticatedUser;
import org.mjulikelion.week3assignment.authentication.AuthenticationExtractor;
import org.mjulikelion.week3assignment.authentication.JwtEncoder;
import org.mjulikelion.week3assignment.dto.requset.user.UserCreateDto;
import org.mjulikelion.week3assignment.dto.requset.user.UserLoginDto;
import org.mjulikelion.week3assignment.dto.response.ResponseDto;
import org.mjulikelion.week3assignment.dto.response.token.TokenResponseDto;
import org.mjulikelion.week3assignment.model.User;
import org.mjulikelion.week3assignment.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/users")
public class AuthController {

    private final UserService userService;

    public static void addCookie(String token, HttpServletResponse response) {
        Cookie cookie = new Cookie(AuthenticationExtractor.TOKEN_COOKIE_NAME, token);
        cookie.setPath("/"); // 모든 경로에서 쿠키 사용 가능
        cookie.setHttpOnly(true); // 쿠키는 HTTP 요청과 함께여야지만 전송 가능하게
        log.info("addCookie 메서드 cookie={}", cookie.getValue());

        response.addCookie(cookie);
    }

    @PostMapping("/join")
    public ResponseEntity<ResponseDto<Void>> createUser(@Valid @RequestBody UserCreateDto userCreateDto) {
        userService.createUser(userCreateDto);
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.CREATED, "사용자 생성 완료"), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseDto<TokenResponseDto>> login(@Valid @RequestBody UserLoginDto userLoginDto, HttpServletResponse response) throws UnsupportedEncodingException {
        TokenResponseDto tokenResponseDto = userService.login(userLoginDto);
        String bearerToken = JwtEncoder.encodeJwtBearerToken(tokenResponseDto.getAccessToken());
        log.info("컨트롤러 accessToken = {}", bearerToken);
        // 쿠키 생성하기
        addCookie(bearerToken, response);
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.OK, "사용자 로그인 완료", tokenResponseDto), HttpStatus.OK);
    }

    @GetMapping("/logout")
    public ResponseEntity<ResponseDto<Void>> logout(HttpServletResponse response) {
        Cookie myCookie = new Cookie(AuthenticationExtractor.TOKEN_COOKIE_NAME, null); // 값 null 로 처리
        myCookie.setMaxAge(0); // 만료시간 0으로 삭제
        myCookie.setPath("/"); // 모든 경로에서 삭제
        response.addCookie(myCookie);
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.OK, "사용자 로그아웃 완료"), HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<ResponseDto<Void>> deleteUser(@AuthenticatedUser User user) {
        userService.deleteUser(user.getId());
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.OK, "사용자 삭제 완료"), HttpStatus.OK);
    }
}
