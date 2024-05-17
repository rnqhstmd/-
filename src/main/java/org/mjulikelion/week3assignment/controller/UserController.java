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
import org.mjulikelion.week3assignment.dto.requset.user.UserUpdateDto;
import org.mjulikelion.week3assignment.dto.response.ResponseDto;
import org.mjulikelion.week3assignment.dto.response.token.TokenResponseDto;
import org.mjulikelion.week3assignment.model.User;
import org.mjulikelion.week3assignment.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;


    public static void addCookie(String token, HttpServletResponse response) {
        String cookieValue = URLEncoder.encode(token, StandardCharsets.UTF_8).replaceAll("\\+", "%20");

        Cookie cookie = new Cookie(AuthenticationExtractor.TOKEN_COOKIE_NAME, cookieValue);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
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
        String accessToken = JwtEncoder.encodeJwtBearerToken(tokenResponseDto.getAccessToken());
        log.info("컨트롤러 accessToken = {}", accessToken);
        // 쿠키 생성하기
        addCookie(accessToken, response);
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.OK, "사용자 로그인 완료", tokenResponseDto), HttpStatus.OK);
    }

    @PatchMapping
    public ResponseEntity<ResponseDto<Void>> updateUser(@AuthenticatedUser User user,
                                                        @Valid @RequestBody UserUpdateDto userUpdateDto) {
        userService.updateUser(user, userUpdateDto);
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.OK, "사용자 수정 완료"), HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<ResponseDto<Void>> deleteUser(@AuthenticatedUser User user) {
        userService.deleteUser(user.getId());
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.OK, "사용자 삭제 완료"), HttpStatus.OK);
    }
}
