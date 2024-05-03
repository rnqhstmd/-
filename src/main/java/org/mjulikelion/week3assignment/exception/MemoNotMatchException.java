package org.mjulikelion.week3assignment.exception;

import org.mjulikelion.week3assignment.exception.errorcode.ErrorCode;

public class MemoNotMatchException extends CustomException {
    public MemoNotMatchException(ErrorCode errorCode, String detail) {
        super(errorCode, detail);
    }
}
