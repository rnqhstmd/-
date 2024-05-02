package org.mjulikelion.week3assignment.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserResponseDto<T> {

    private final String statusCode;
    private final String message; // API 요청 결과

    // 반환 데이터가 없는 API를 위한 응답
    public static <T> UserResponseDto<T> res(final String statusCode, final String message) {
        return new UserResponseDto<>(statusCode, message);
    }
}
