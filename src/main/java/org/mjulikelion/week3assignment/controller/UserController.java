package org.mjulikelion.week3assignment.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.mjulikelion.week3assignment.dto.requset.user.UserCreateDto;
import org.mjulikelion.week3assignment.dto.requset.user.UserLoginDto;
import org.mjulikelion.week3assignment.dto.requset.user.UserUpdateDto;
import org.mjulikelion.week3assignment.dto.response.ResponseDto;
import org.mjulikelion.week3assignment.dto.response.user.UserResponseData;
import org.mjulikelion.week3assignment.service.OrganizationService;
import org.mjulikelion.week3assignment.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final OrganizationService organizationService;

    @PostMapping("join")
    public ResponseEntity<ResponseDto<Void>> createUser(@Valid @RequestBody UserCreateDto userCreateDto) {
        userService.createUser(userCreateDto);
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.CREATED, "사용자 생성 완료"), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseDto<UserResponseData>> login(@Valid @RequestBody UserLoginDto userLoginDto) {
        userService.login(userLoginDto);
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.CREATED, "사용자 로그인 완료"), HttpStatus.CREATED);
    }

    @PatchMapping
    public ResponseEntity<ResponseDto<Void>> updateUser(@RequestHeader("User-Id") UUID id, @Valid @RequestBody UserUpdateDto userUpdateDto) {
        userService.updateUser(id, userUpdateDto);
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.OK, "사용자 수정 완료"), HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<ResponseDto<Void>> deleteUser(@RequestHeader("User-Id") UUID id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.OK, "사용자 삭제 완료"), HttpStatus.OK);
    }
}
