package org.mjulikelion.week3assignment.user.controller;

import lombok.AllArgsConstructor;
import org.mjulikelion.week3assignment.user.dto.UserCreateDto;
import org.mjulikelion.week3assignment.user.dto.UserResponseDto;
import org.mjulikelion.week3assignment.user.dto.UserUpdateDto;
import org.mjulikelion.week3assignment.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserResponseDto<Void>> createUser(@RequestBody UserCreateDto userCreateDto) {
        userService.createUser(userCreateDto);
        return new ResponseEntity<>(UserResponseDto.res(String.valueOf(HttpStatus.CREATED), "사용자 생성 완료"), HttpStatus.CREATED);
    }

    @PatchMapping("/{userId")
    public ResponseEntity<UserResponseDto<Void>> updateUser(@RequestHeader String userid, @RequestBody UserUpdateDto userUpdateDto) {
        userService.updateUser(userid, userUpdateDto.getNewUserId(), userUpdateDto);
        return new ResponseEntity<>(UserResponseDto.res(String.valueOf(HttpStatus.CREATED), "사용자 수정 완료"), HttpStatus.CREATED);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<UserResponseDto<Void>> deleteUser(@RequestHeader String userId) {
        userService.deleteUser(userId);
        return new ResponseEntity<>(UserResponseDto.res(String.valueOf(HttpStatus.ACCEPTED), "사용자 삭제 완료"), HttpStatus.ACCEPTED);
    }
}
