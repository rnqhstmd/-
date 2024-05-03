package org.mjulikelion.week3assignment.exception;

import org.mjulikelion.week3assignment.exception.errorcode.ErrorCode;

public class LikeNotFoundException extends CustomException {
    public LikeNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}
