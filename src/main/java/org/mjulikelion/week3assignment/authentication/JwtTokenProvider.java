package org.mjulikelion.week3assignment.authentication;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.mjulikelion.week3assignment.exception.UnauthorizedException;
import org.mjulikelion.week3assignment.exception.errorcode.ErrorCode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Slf4j
@Component
/**
 * 역할
 * 1. 정보를 받아 해당 정보를 Payload 에 담아 jwt token을 발급
 * 2. jwt token 을 받아 복호화하여 정보를 반환
 */
public class JwtTokenProvider {
    private final SecretKey key; // 시크릿 키
    private final long validityInMilliseconds; // 유효 시간

    public JwtTokenProvider(@Value("${security.jwt.token.secret-key}") final String secretKey,
                            @Value("${security.jwt.token.expire-length}") final long validityInMilliseconds) {
        this.key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
        this.validityInMilliseconds = validityInMilliseconds;
    }

    // 토큰 생성
    public String createToken(final String payload) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + validityInMilliseconds);

        return Jwts.builder()
                .setSubject(payload)
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(SignatureAlgorithm.HS256, key)
                .compact();
    }

    // 정보 추출
    public String getPayload(final String token) {
        try {
            String payload = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
            log.info("payload 값={}", payload);
            return payload;
        } catch (JwtException e) {
            throw new UnauthorizedException(ErrorCode.INVALID_TOKEN, e.getMessage());
        }
    }
}
