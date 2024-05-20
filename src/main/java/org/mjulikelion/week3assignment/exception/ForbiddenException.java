package org.mjulikelion.week3assignment.exception;

import org.mjulikelion.week3assignment.exception.errorcode.ErrorCode;

public class ForbiddenException extends CustomException {
    public ForbiddenException(ErrorCode errorCode) {
        super(errorCode);
    }
}
