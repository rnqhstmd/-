package org.mjulikelion.week3assignment.authentication;

import lombok.AllArgsConstructor;
import org.mjulikelion.week3assignment.exception.UnauthorizedException;
import org.mjulikelion.week3assignment.exception.errorcode.ErrorCode;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class JwtEncoder {

    public static final String TOKEN_TYPE = "Bearer:";

    public static String decodeJwtBearerToken(String cookie) {
        if (cookie != null && cookie.startsWith(TOKEN_TYPE)) {
            return cookie.substring(TOKEN_TYPE.length());
        }
        throw new UnauthorizedException(ErrorCode.TOKEN_NOT_FOUND, "JWT 토큰을 찾을 수 없습니다.");
    }
}
