package org.mjulikelion.week3assignment.authentication;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.mjulikelion.week3assignment.exception.UnauthorizedException;
import org.mjulikelion.week3assignment.exception.errorcode.ErrorCode;

@Slf4j
public class AuthenticationExtractor {
    public static final String TOKEN_HEADER_NAME = "Authorization";
    public static final String TOKEN_TYPE = "Bearer ";

    public static String extract(final HttpServletRequest request) {
        String token = request.getHeader(TOKEN_HEADER_NAME);
        if (token != null && token.startsWith(TOKEN_TYPE)) {
            return token.substring(TOKEN_TYPE.length());
        }
        throw new UnauthorizedException(ErrorCode.TOKEN_NOT_FOUND, "");
    }
}
