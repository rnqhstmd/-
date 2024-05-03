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

    // UserNotFoundException 처리
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleUserNotFoundException(UserNotFoundException userNotFoundException) {
        this.writeLog(userNotFoundException);
        return new ResponseEntity<>(ErrorResponseDto.res(userNotFoundException), HttpStatus.NOT_FOUND);
    }

    // MemoNotFoundException 처리
    @ExceptionHandler(MemoNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleMemoNotFoundException(MemoNotFoundException memoNotFoundException) {
        this.writeLog(memoNotFoundException);
        return new ResponseEntity<>(ErrorResponseDto.res(memoNotFoundException), HttpStatus.NOT_FOUND);
    }

    // LikeNotFoundException 처리
    @ExceptionHandler(LikeNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleLikeNotFoundException(LikeNotFoundException likeNotFoundException) {
        this.writeLog(likeNotFoundException);
        return new ResponseEntity<>(ErrorResponseDto.res(likeNotFoundException), HttpStatus.NOT_FOUND);
    }

    // UserNotMatchException 처리
    @ExceptionHandler(UserNotMatchException.class)
    public ResponseEntity<ErrorResponseDto> handleUserNotMatchException(UserNotMatchException userNotMatchException) {
        this.writeLog(userNotMatchException);
        return new ResponseEntity<>(ErrorResponseDto.res(userNotMatchException), HttpStatus.BAD_REQUEST);
    }

    // MemoNotMatchException 처리
    @ExceptionHandler(MemoNotMatchException.class)
    public ResponseEntity<ErrorResponseDto> handleMemoNotMatchException(MemoNotMatchException memoNotMatchException) {
        this.writeLog(memoNotMatchException);
        return new ResponseEntity<>(ErrorResponseDto.res(memoNotMatchException), HttpStatus.BAD_REQUEST);
    }

    // UserAlreadyExistsException 처리
    @ExceptionHandler(UserAlreadyExists.class)
    public ResponseEntity<ErrorResponseDto> handleUserAlreadyExists(UserAlreadyExists userAlreadyExists) {
        this.writeLog(userAlreadyExists);
        return new ResponseEntity<>(ErrorResponseDto.res(userAlreadyExists), HttpStatus.BAD_REQUEST);
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
