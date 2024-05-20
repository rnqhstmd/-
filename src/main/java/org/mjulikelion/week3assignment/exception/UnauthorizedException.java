package org.mjulikelion.week3assignment.exception;

import org.mjulikelion.week3assignment.exception.errorcode.ErrorCode;

public class UnauthorizedException extends CustomException {
    public UnauthorizedException(ErrorCode errorCode, String detail) {
        super(errorCode, detail);
    }
}
