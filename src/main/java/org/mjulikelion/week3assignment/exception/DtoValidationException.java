package org.mjulikelion.week3assignment.exception;

import org.mjulikelion.week3assignment.errorcode.ErrorCode;

public class DtoValidationException extends CustomException {

    // Dto 검증예외
    public DtoValidationException(ErrorCode errorCode, String detail) {
        super(errorCode, detail);
    }
}
