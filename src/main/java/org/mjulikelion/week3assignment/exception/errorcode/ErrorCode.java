package org.mjulikelion.week3assignment.exception.errorcode;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    //NotFoundException
    USER_NOT_FOUND("4040", "유저를 찾을 수 없습니다."),
    MEMO_NOT_FOUND("4041", "메모를 찾을 수 없습니다."),
    LIKE_NOT_FOUND("4042", "좋아요를 찾을 수 없습니다."),
    ORGANIZATION_NOT_FOUND("4043", "소속을 찾을 수 없습니다."),

    //ForbiddenException
    USER_NOT_MATCH("4030", "해당 유저에 대한 접근 권한이 없습니다."),
    MEMO_NOT_MATCH("4031", "해당 메모에 대한 접근 권한이 없습니다."),

    //ConflictException
    USER_ALREADY_EXISTS("4090", "이미 존재하는 유저입니다."),
    LIKE_ALREADY_EXISTS("4091", "좋아요가 이미 존재합니다."),
    ORGANIZATION_ALREADY_EXISTS("4092", "이미 존재하는 소속입니다."),

    //UnauthorizedException
    INVALID_EMAIL_OR_PASSWORD("4010", "유효하지 않은 이메일이나 패스워드입니다."),
    INVALID_TOKEN("4011", "유효하지 않은 토큰입니다."),
    TOKEN_NOT_FOUND("4044", "토큰을 찾을 수 없습니다."),

    NOT_NULL("9001", "필수값이 누락되었습니다."),
    NOT_BLANK("9002", "필수값이 빈 값이거나 공백으로 되어있습니다."),
    REGEX("9003", "형식에 맞지 않습니다."),
    LENGTH("9004", "길이가 유효하지 않습니다.");

    private final String code;
    private final String message;

    public static ErrorCode dtoValidationErrorCode(String code) {
        return switch (code) {
            case "NOTNULL" -> NOT_NULL;
            case "NotBlank" -> NOT_BLANK;
            case "Pattern" -> REGEX;
            case "Length" -> LENGTH;
            default -> throw new IllegalStateException("Unexpected value: " + code);
        };
    }
}
