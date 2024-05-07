package org.mjulikelion.week3assignment.exception;

import org.mjulikelion.week3assignment.exception.errorcode.ErrorCode;

public class OrganizationNotFoundException extends CustomException {
    public OrganizationNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}
