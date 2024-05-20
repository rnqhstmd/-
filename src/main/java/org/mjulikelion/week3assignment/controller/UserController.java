package org.mjulikelion.week3assignment.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mjulikelion.week3assignment.authentication.AuthenticatedUser;
import org.mjulikelion.week3assignment.dto.requset.user.UserUpdateDto;
import org.mjulikelion.week3assignment.dto.response.ResponseDto;
import org.mjulikelion.week3assignment.model.User;
import org.mjulikelion.week3assignment.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @PatchMapping
    public ResponseEntity<ResponseDto<Void>> updateUser(@AuthenticatedUser User user,
                                                        @Valid @RequestBody UserUpdateDto userUpdateDto) {
        userService.updateUser(user, userUpdateDto);
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.OK, "사용자 수정 완료"), HttpStatus.OK);
    }
}
