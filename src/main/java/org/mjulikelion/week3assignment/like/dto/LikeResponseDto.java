package org.mjulikelion.week3assignment.like.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatusCode;

@Getter
@AllArgsConstructor
public class LikeResponseDto<T> {
    private final String statusCode;
    private final String message;

    // 반환 데이터가 없는 API를 위한 응답
    public static <T> LikeResponseDto<T> res(final HttpStatusCode statusCode, final String message) {
        return new LikeResponseDto<>(statusCode.toString(), message);
    }
}
