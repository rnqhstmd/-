package org.mjulikelion.week3assignment.service;

import lombok.AllArgsConstructor;
import org.mjulikelion.week3assignment.authentication.AuthenticatedUser;
import org.mjulikelion.week3assignment.authentication.JwtTokenProvider;
import org.mjulikelion.week3assignment.dto.requset.user.UserCreateDto;
import org.mjulikelion.week3assignment.dto.requset.user.UserLoginDto;
import org.mjulikelion.week3assignment.dto.requset.user.UserUpdateDto;
import org.mjulikelion.week3assignment.dto.response.token.TokenResponseDto;
import org.mjulikelion.week3assignment.dto.response.user.UserResponseData;
import org.mjulikelion.week3assignment.exception.ConflictException;
import org.mjulikelion.week3assignment.exception.NotFoundException;
import org.mjulikelion.week3assignment.exception.UnauthorizedException;
import org.mjulikelion.week3assignment.exception.errorcode.ErrorCode;
import org.mjulikelion.week3assignment.model.User;
import org.mjulikelion.week3assignment.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;

    private static void validateLoginUser(UserLoginDto userLoginDto, User user) {
        if (!userLoginDto.getPassword().matches(user.getPassword()) && !userLoginDto.getEmail().matches(user.getEmail())) {
            throw new UnauthorizedException(ErrorCode.INVALID_EMAIL_OR_PASSWORD, "로그인하려했으나 이메일이나 패스워드가 유효하지 않습니다.");
        }
    }

    // 회원가입
    public UserResponseData createUser(UserCreateDto userCreateDto) {

        validateEmailNotExists(userCreateDto.getEmail());

        User user = User.builder()
                .email(userCreateDto.getEmail())
                .name(userCreateDto.getName())
                .password(userCreateDto.getPassword())
                .build();
        userRepository.save(user);
        return new UserResponseData(user.getEmail(), user.getName());
    }

    // 회원 로그인
    public TokenResponseDto login(UserLoginDto userLoginDto) {
        User user = getUserByEmail(userLoginDto.getEmail());
        validateLoginUser(userLoginDto, user);

        String token = jwtTokenProvider.createToken(String.valueOf(user.getId()));

        // 토큰 반환
        return new TokenResponseDto(token);
    }

    // 회원 정보 수정
    public UserResponseData updateUser(@AuthenticatedUser User authenticatedUser, UserUpdateDto userUpdateDto) {

        User user = validateUser(authenticatedUser.getId());
        user.setName(userUpdateDto.getNewName());

        // 이미 존재하는 이메일과 기존과 동일한 이메일 검증
        if (userRepository.existsByEmail(userUpdateDto.getNewEmail()) && userUpdateDto.getNewEmail().matches(user.getEmail())) {
            throw new ConflictException(ErrorCode.USER_ALREADY_EXISTS);
        }

        user.setEmail(userUpdateDto.getNewEmail());
        userRepository.save(user);
        return new UserResponseData(user.getEmail(), user.getName());
    }

    // 회원 탈퇴
    public void deleteUser(UUID id) {
        User user = validateUser(id);
        userRepository.delete(user);
    }

    // 유저 id로 존재 검증
    private User validateUser(UUID userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND));
    }

    // 수정 시 존재하는 이메일 검증
    private void validateEmailNotExists(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new ConflictException(ErrorCode.USER_ALREADY_EXISTS);
        }
    }

    // 이메일로 유저 가져오기
    private User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ConflictException(ErrorCode.USER_ALREADY_EXISTS));
    }
}
