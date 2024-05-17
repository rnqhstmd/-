package org.mjulikelion.week3assignment.authentication;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mjulikelion.week3assignment.exception.UnauthorizedException;
import org.mjulikelion.week3assignment.exception.errorcode.ErrorCode;

@Slf4j
@AllArgsConstructor
public class AuthenticationExtractor {
    public static final String TOKEN_COOKIE_NAME = "AccessToken";

    public static String extract(final HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (TOKEN_COOKIE_NAME.equals(cookie.getName())) {
                    return JwtEncoder.decodeJwtBearerToken(cookie.getValue());
                }
            }
        }
        throw new UnauthorizedException(ErrorCode.TOKEN_NOT_FOUND, "");
    }
}
