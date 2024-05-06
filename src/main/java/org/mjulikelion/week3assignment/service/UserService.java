package org.mjulikelion.week3assignment.service;

import lombok.AllArgsConstructor;
import org.mjulikelion.week3assignment.dto.requset.user.UserCreateDto;
import org.mjulikelion.week3assignment.dto.requset.user.UserLoginDto;
import org.mjulikelion.week3assignment.dto.requset.user.UserUpdateDto;
import org.mjulikelion.week3assignment.dto.response.user.UserResponseDto;
import org.mjulikelion.week3assignment.exception.InvalidEmailOrPasswordException;
import org.mjulikelion.week3assignment.exception.UserAlreadyExistsException;
import org.mjulikelion.week3assignment.exception.UserNotFoundException;
import org.mjulikelion.week3assignment.exception.errorcode.ErrorCode;
import org.mjulikelion.week3assignment.model.User;
import org.mjulikelion.week3assignment.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    // 회원가입
    public void createUser(UserCreateDto userCreateDto) {
        if (userRepository.findByEmail(userCreateDto.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException(ErrorCode.USER_ALREADY_EXISTS, "이미 존재하는 이메일입니다.");
        }

        User user = User.builder()
                .email(userCreateDto.getEmail())
                .name(userCreateDto.getName())
                .password(userCreateDto.getPassword())
                .build();
        userRepository.save(user);
        return;
        new UserResponseDto(user.getId(), user.getEmail(), user.getName());
    }

    // 회원 로그인
    public UserResponseDto login(UserLoginDto userLoginDto) {
        User user = userRepository.findByEmail(userLoginDto.getEmail())
                .orElseThrow(() -> new UserNotFoundException(ErrorCode.USER_NOT_FOUND));

        if (!userLoginDto.getPassword().matches(user.getPassword()) && !userLoginDto.getEmail().matches(user.getEmail())) {
            throw new InvalidEmailOrPasswordException(ErrorCode.INVALID_EMAIL_OR_PASSWORD);
        }

        return new UserResponseDto(user.getId(), user.getEmail(), user.getName());
    }

    // 회원 정보 수정
    public UserResponseDto updateUser(UUID id, UserUpdateDto userUpdateDto) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(ErrorCode.USER_NOT_FOUND));
        user.setName(userUpdateDto.getNewName());

        // 이미 존재하는 이메일과 기존과 동일한 이메일 검증
        if (userRepository.existsByEmail(userUpdateDto.getNewEmail()) && userUpdateDto.getNewEmail().matches(user.getEmail())) {
            throw new UserAlreadyExistsException(ErrorCode.USER_ALREADY_EXISTS, "이미 존재하는 이메일입니다.");
        }
        user.setEmail(userUpdateDto.getNewEmail());

        userRepository.save(user);
        return new UserResponseDto(user.getId(), user.getEmail(), user.getName());
    }

    // 회원 탈퇴
    public void deleteUser(UUID id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(ErrorCode.USER_NOT_FOUND));

        userRepository.delete(user);
    }
}
