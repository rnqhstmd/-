package org.mjulikelion.week3assignment.exception.exception;

import org.mjulikelion.week3assignment.exception.errorcode.ErrorCode;

public class DtoValidationException extends CustomException {

    // Dto 검증예외
    public DtoValidationException(ErrorCode errorCode, String detail) {
        super(errorCode, detail);
    }
}
