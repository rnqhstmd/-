package org.mjulikelion.week3assignment.exception;

import org.mjulikelion.week3assignment.exception.errorcode.ErrorCode;

public class UserAlreadyExists extends CustomException {
    public UserAlreadyExists(ErrorCode errorCode) {
        super(errorCode);
    }
}
