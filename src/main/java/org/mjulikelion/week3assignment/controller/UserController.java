package org.mjulikelion.week3assignment.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
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

@RestController
@AllArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @PostMapping("/join")
    public ResponseEntity<ResponseDto<Void>> createUser(@Valid @RequestBody UserCreateDto userCreateDto) {
        userService.createUser(userCreateDto);
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.CREATED, "사용자 생성 완료"), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseDto<TokenResponseDto>> login(@Valid @RequestBody UserLoginDto userLoginDto, HttpServletResponse response) {
        TokenResponseDto tokenResponseDto = userService.login(userLoginDto);
        String bearerToken = JwtEncoder.TOKEN_TYPE + tokenResponseDto.getAccessToken();
        Cookie cookie = new Cookie(AuthenticationExtractor.TOKEN_COOKIE_NAME, bearerToken);
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.OK, "사용자 로그인 완료", tokenResponseDto), HttpStatus.CREATED);
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
