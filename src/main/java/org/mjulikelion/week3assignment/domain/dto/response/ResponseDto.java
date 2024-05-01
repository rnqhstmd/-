package org.mjulikelion.week3assignment.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatusCode;

@Getter
@AllArgsConstructor
public class ResponseDto<T> {
    private final String statusCode;
    private final String message; // API 요청 결과
    private final T data; // API 요청 결과 데이터

    // 반환 데이터가 없는 API를 위한 응답
    public static <T> ResponseDto<T> res(final HttpStatusCode statusCode, final String message) {
        return new ResponseDto<>(statusCode.toString(), message, null);
    }

    // 반환 데이터가 있는 API를 위한 응답
    public static <T> ResponseDto<T> res(final HttpStatusCode statusCode, final String message, final T data) {
        return new ResponseDto<>(statusCode.toString(), message, data);
    }

}
