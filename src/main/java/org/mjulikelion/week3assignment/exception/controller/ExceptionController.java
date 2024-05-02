package org.mjulikelion.week3assignment.exception.controller;

import lombok.extern.slf4j.Slf4j;
import org.mjulikelion.week3assignment.exception.dto.ErrorResponseDto;
import org.mjulikelion.week3assignment.exception.errorcode.ErrorCode;
import org.mjulikelion.week3assignment.exception.exception.DtoValidationException;
import org.mjulikelion.week3assignment.exception.exception.MemoNotFoundException;
import org.mjulikelion.week3assignment.exception.exception.UserAlreadyExists;
import org.mjulikelion.week3assignment.exception.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ExceptionController {

    // UserNotFoundException 처리
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleUserNotFoundException(UserNotFoundException userNotFoundException) {
        return new ResponseEntity<>(ErrorResponseDto.res(userNotFoundException), HttpStatus.NOT_FOUND);
    }

    // MemoNotFoundException 처리
    @ExceptionHandler(MemoNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleMemoNotFoundException(MemoNotFoundException memoNotFoundException) {
        return new ResponseEntity<>(ErrorResponseDto.res(memoNotFoundException), HttpStatus.NOT_FOUND);
    }

    // UserAlreadyExistsException 처리
    @ExceptionHandler(UserAlreadyExists.class)
    public ResponseEntity<ErrorResponseDto> handleUserAlreadyExists(UserAlreadyExists userAlreadyExists) {
        return new ResponseEntity<>(ErrorResponseDto.res(userAlreadyExists), HttpStatus.BAD_REQUEST);
    }

    // 일반 예외(커스텀한 예외 제외)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleException(Exception exception) {
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
        return new ResponseEntity<>(ErrorResponseDto.res(dtoValidationException), HttpStatus.BAD_REQUEST);
    }

}
