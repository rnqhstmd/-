package org.mjulikelion.week3assignment.exception;

import org.mjulikelion.week3assignment.exception.errorcode.ErrorCode;

public class UserNotMatchException extends CustomException {
    public UserNotMatchException(ErrorCode errorCode, String detail) {
        super(errorCode, detail);
    }
}
