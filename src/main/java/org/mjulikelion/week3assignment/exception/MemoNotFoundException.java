package org.mjulikelion.week3assignment.exception;

import org.mjulikelion.week3assignment.exception.errorcode.ErrorCode;

public class MemoNotFoundException extends CustomException {
    public MemoNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}
