package org.mjulikelion.week3assignment.exception.errorcode;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    USER_NOT_FOUND("4040", "유저를 찾을 수 없습니다."),
    MEMO_NOT_FOUND("4041", "메모를 찾을 수 없습니다."),
    LIKE_NOT_FOUND("4042", "좋아요를 찾을 수 없습니다."),
    USER_NOT_MATCH("4050", "유저가 일치하지 않습니다."),
    MEMO_NOT_MATCH("4051", "메모가 일치하지 않습니다."),
    USER_ALREADY_EXISTS("4060", "이미 존재하는 유저입니다."),
    INVALID_EMAIL_OR_PASSWORD("4080", "유효하지 않은 이메일이나 패스워드입니다."),
    LIKE_ALREADY_EXISTS("4090", "좋아요가 이미 존재합니다."),
    ORGANIZATION_NOT_FOUND("4070", "소속을 찾을 수 없습니다."),
    ORGANIZATION_ALREADY_EXISTS("4071", "이미 존재하는 소속입니다."),
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
