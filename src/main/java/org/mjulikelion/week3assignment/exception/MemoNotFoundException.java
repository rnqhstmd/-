package org.mjulikelion.week3assignment.exception;

import org.mjulikelion.week3assignment.errorcode.ErrorCode;

public class MemoNotFoundException extends CustomException {
    public MemoNotFoundException(ErrorCode errorCode, String detail) {
        super(errorCode, detail);
    }
}
