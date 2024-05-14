package org.mjulikelion.week3assignment.authentication;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mjulikelion.week3assignment.exception.NotFoundException;
import org.mjulikelion.week3assignment.exception.errorcode.ErrorCode;
import org.mjulikelion.week3assignment.model.User;
import org.mjulikelion.week3assignment.repository.UserRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.UUID;

@Slf4j
@AllArgsConstructor
@Component
public class AuthenticationInterceptor implements HandlerInterceptor {
    private final UserRepository userRepository;
    private final AuthenticationContext authenticationContext;
    private JwtTokenProvider jwtTokenProvider;

    /**
     * 1. request에서 accessToken 추출
     * 2. token 에서 payload 추출
     * 3. userId를 통해 user 검증, 추출
     * 4. AuthenticationContext 에 User 저장
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String accessToken = AuthenticationExtractor.extract(request);
        log.info("accessToken 추출 성공:{}", accessToken);
        UUID userId = UUID.fromString(jwtTokenProvider.getPayload(accessToken));
        log.info(" UserId 추출: {}", userId);
        User user = findExistingUser(userId);
        authenticationContext.setPrincipal(user);
        return true;
    }

    private User findExistingUser(UUID userId) {
        return userRepository.findById(userId).orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND));
    }
}
