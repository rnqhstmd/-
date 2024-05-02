package org.mjulikelion.week3assignment.exception.exception;

import org.mjulikelion.week3assignment.exception.errorcode.ErrorCode;

public class UserAlreadyExists extends CustomException {
    public UserAlreadyExists(ErrorCode errorCode, String detail) {
        super(errorCode, detail);
    }
}
