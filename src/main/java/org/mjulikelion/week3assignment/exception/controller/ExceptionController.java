package org.mjulikelion.week3assignment.exception.controller;

import lombok.extern.slf4j.Slf4j;
import org.mjulikelion.week3assignment.exception.*;
import org.mjulikelion.week3assignment.exception.dto.ErrorResponseDto;
import org.mjulikelion.week3assignment.exception.errorcode.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ExceptionController {
    private static String extractErrorCode(CustomException customException) {
        return customException.getErrorCode().toString().substring(0, 2);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleNotFoundException(NotFoundException notFoundException) {
        this.writeLog(notFoundException);
        return new ResponseEntity<>(ErrorResponseDto.res(notFoundException), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ErrorResponseDto> handleConflictException(ConflictException conflictException) {
        this.writeLog(conflictException);
        return new ResponseEntity<>(ErrorResponseDto.res(conflictException), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<ErrorResponseDto> handleForbiddenException(ForbiddenException forbiddenException) {
        this.writeLog(forbiddenException);
        return new ResponseEntity<>(ErrorResponseDto.res(forbiddenException), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ErrorResponseDto> handleUnauthorizedException(UnauthorizedException unauthorizedException) {
        this.writeLog(unauthorizedException);
        return new ResponseEntity<>(ErrorResponseDto.res(unauthorizedException), HttpStatus.UNAUTHORIZED);
    }

    // 일반 예외(커스텀한 예외 제외)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleException(Exception exception) {
        this.writeLog(exception);
        return new ResponseEntity<>(ErrorResponseDto.res(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()), exception)
                , HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // Dto 검증 어노테이션 예외 제외
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDto> handleMethodArgumentNotValidException(MethodArgumentNotValidException methodArgumentNotValidException) {
        FieldError fieldError = methodArgumentNotValidException.getBindingResult().getFieldError();
        if (fieldError == null) {
            return new ResponseEntity<>(ErrorResponseDto.res(String.valueOf(HttpStatus.BAD_REQUEST.value())
                    , methodArgumentNotValidException), HttpStatus.BAD_REQUEST);
        }
        ErrorCode errorCode = ErrorCode.dtoValidationErrorCode(fieldError.getCode());
        String detail = fieldError.getDefaultMessage();
        DtoValidationException dtoValidationException = new DtoValidationException(errorCode, detail);

        this.writeLog(dtoValidationException);

        return new ResponseEntity<>(ErrorResponseDto.res(dtoValidationException), HttpStatus.BAD_REQUEST);
    }


    private void writeLog(CustomException customException) {
        String exceptionName = customException.getClass().getSimpleName();
        ErrorCode errorCode = customException.getErrorCode();
        String detail = customException.getDetail();

        log.error("({}){}: {}", exceptionName, errorCode.getMessage(), detail);
    }

    private void writeLog(Exception exception) {
        log.error("({}){}", exception.getClass().getSimpleName(), exception.getMessage());
    }
}
