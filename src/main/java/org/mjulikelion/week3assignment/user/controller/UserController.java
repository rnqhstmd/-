package org.mjulikelion.week3assignment.user.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.mjulikelion.week3assignment.response.ResponseDto;
import org.mjulikelion.week3assignment.user.dto.UserCreateDto;
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
    public ResponseEntity<ResponseDto<Void>> createUser(@RequestBody UserCreateDto userCreateDto) {
        userService.createUser(userCreateDto);
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.CREATED, "사용자 생성 완료"), HttpStatus.CREATED);
    }

    @PatchMapping("/userInfo")
    public ResponseEntity<ResponseDto<Void>> updateUser(@RequestHeader("UserId") String userId, @Valid @RequestBody UserUpdateDto userUpdateDto) {
        userService.updateUser(userId, userUpdateDto);
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.OK, "사용자 수정 완료"), HttpStatus.OK);
    }

    @DeleteMapping("/userInfo")
    public ResponseEntity<ResponseDto<Void>> deleteUser(@RequestHeader String userId) {
        userService.deleteUser(userId);
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.OK, "사용자 삭제 완료"), HttpStatus.OK);
    }
}
