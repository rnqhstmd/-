package org.mjulikelion.week3assignment.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mjulikelion.week3assignment.authentication.JwtTokenProvider;
import org.mjulikelion.week3assignment.authentication.PasswordHashEncryption;
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

@Slf4j
@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordHashEncryption passwordHashEncryption;


    // 회원가입
    public UserResponseData createUser(UserCreateDto userCreateDto) {
        // 존재하는 이메일 검증
        validateEmailExists(userCreateDto.getEmail());

        // 패스워드 암호화
        String plainPassword = userCreateDto.getPassword();
        String EncryptedPassword = passwordHashEncryption.encrypt(plainPassword);

        User user = User.builder()
                .email(userCreateDto.getEmail())
                .name(userCreateDto.getName())
                .password(EncryptedPassword)
                .build();
        userRepository.save(user);
        return new UserResponseData(user);
    }

    // 회원 로그인
    public TokenResponseDto login(UserLoginDto userLoginDto) {
        // 이메일로 유저 가져오기
        User user = getUserByEmail(userLoginDto.getEmail());

        // 비밀번호 검증
        String plainPassword = userLoginDto.getPassword();
        if (!passwordHashEncryption.matches(plainPassword, user.getPassword())) {
            throw new UnauthorizedException(ErrorCode.INVALID_PASSWORD, "로그인하려했으나 패스워드가 유효하지 않습니다.");
        }

        // 토큰 생성
        String token = jwtTokenProvider.createToken(String.valueOf(user.getId()));
        log.info("login 메서드 token 생성 ={}", token);

        // 토큰 반환
        return new TokenResponseDto(token);
    }

    // 회원 정보 수정
    public UserResponseData updateUser(User user, UserUpdateDto userUpdateDto) {
        // 이미 존재하는 이름과 기존의 동일한 이름 검증
        if (userRepository.existsByName(user.getName()) && userUpdateDto.getNewName().matches(user.getName())) {
            throw new ConflictException(ErrorCode.USER_ALREADY_EXISTS);
        }
        user.setName(userUpdateDto.getNewName());

        // 이미 존재하는 이메일과 기존과 동일한 이메일 검증
        if (userRepository.existsByEmail(userUpdateDto.getNewEmail()) && userUpdateDto.getNewEmail().matches(user.getEmail())) {
            throw new ConflictException(ErrorCode.USER_ALREADY_EXISTS);
        }
        user.setEmail(userUpdateDto.getNewEmail());

        userRepository.save(user);

        return new UserResponseData(user);
    }

    // 회원 탈퇴
    public void deleteUser(UUID id) {
        userRepository.deleteById(id);
    }

    private void validateEmailExists(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new ConflictException(ErrorCode.USER_ALREADY_EXISTS);
        }
    }

    private User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND));
    }
}
