package org.mjulikelion.week3assignment.exception;

import org.mjulikelion.week3assignment.exception.errorcode.ErrorCode;

public class InvalidEmailOrPasswordException extends CustomException {
    public InvalidEmailOrPasswordException(ErrorCode errorCode) {
        super(errorCode);
    }
}
