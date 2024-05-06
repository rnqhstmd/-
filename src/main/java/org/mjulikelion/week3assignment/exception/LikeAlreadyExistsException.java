package org.mjulikelion.week3assignment.exception;

import org.mjulikelion.week3assignment.exception.errorcode.ErrorCode;

public class LikeAlreadyExistsException extends CustomException {

    public LikeAlreadyExistsException(ErrorCode errorCode) {
        super(errorCode);
    }
}
