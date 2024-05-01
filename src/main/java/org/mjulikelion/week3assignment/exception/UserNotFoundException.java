package org.mjulikelion.week3assignment.exception;

import org.mjulikelion.week3assignment.errorcode.ErrorCode;

public class UserNotFoundException extends CustomException {
    public UserNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}
