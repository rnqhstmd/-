package org.mjulikelion.week3assignment.test;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.mjulikelion.week3assignment.exception.NotFoundException;
import org.mjulikelion.week3assignment.exception.errorcode.ErrorCode;
import org.mjulikelion.week3assignment.repository.UserRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.util.UUID;

@Slf4j
@Component
public class Interceptor implements HandlerInterceptor {
    private final UserRepository userRepository;

    public Interceptor(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) {
        String userId = request.getHeader("User-Id");
        if (!userRepository.existsById(UUID.fromString(userId))) {
            throw new NotFoundException(ErrorCode.USER_NOT_FOUND);
        }
        log.info("TestInterceptor preHandle");
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response,
                           Object handler,
                           ModelAndView modelAndView) {
        log.info("TestInterceptor postHandle");
        log.info("response status:{}", response.getStatus()); // 반환된 status code 가져오기
        try {
            response.getOutputStream().print("TestInterceptor postHandle");
        } catch (Exception e) {
            log.info("error");
        }
    }
}
