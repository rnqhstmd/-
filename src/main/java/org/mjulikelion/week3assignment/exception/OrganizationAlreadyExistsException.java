package org.mjulikelion.week3assignment.exception;

import org.mjulikelion.week3assignment.exception.errorcode.ErrorCode;

public class OrganizationAlreadyExistsException extends CustomException {
    public OrganizationAlreadyExistsException(ErrorCode errorCode) {
        super(errorCode);
    }
}
