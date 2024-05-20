package org.mjulikelion.week3assignment.authentication;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mjulikelion.week3assignment.exception.UnauthorizedException;
import org.mjulikelion.week3assignment.exception.errorcode.ErrorCode;
import org.springframework.stereotype.Component;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Slf4j
@AllArgsConstructor
@Component
public class JwtEncoder {

    public static final String TOKEN_TYPE = "Bearer ";

    public static String encodeJwtBearerToken(String token) {
        String cookieValue = TOKEN_TYPE + token;
        return URLEncoder.encode(cookieValue, StandardCharsets.UTF_8).replaceAll("\\+", "%20");
    }

    public static String decodeJwtBearerToken(String cookieValue) {
        String value = URLDecoder.decode(cookieValue, StandardCharsets.UTF_8);
        if (value.startsWith(TOKEN_TYPE)) {
            return value.substring(TOKEN_TYPE.length());
        }
        throw new UnauthorizedException(ErrorCode.INVALID_TOKEN, "decode 하려 했으나 유효하지 않은 토큰입니다.");
    }
}
