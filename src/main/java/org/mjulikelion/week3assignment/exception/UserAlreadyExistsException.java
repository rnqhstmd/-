package org.mjulikelion.week3assignment.exception;

import org.mjulikelion.week3assignment.exception.errorcode.ErrorCode;

public class UserAlreadyExistsException extends CustomException {

    public UserAlreadyExistsException(ErrorCode errorCode, String detail) {
        super(errorCode, detail);
    }
}
