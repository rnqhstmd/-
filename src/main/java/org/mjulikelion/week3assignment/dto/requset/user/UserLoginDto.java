package org.mjulikelion.week3assignment.dto.requset.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserLoginDto {
    @NotNull(message = "사용자 이메일이 비어있습니다.")
    @Email(message = "이메일 형식이어야합니다.")
    private String email;
    @NotNull(message = "사용자 비밀번호가 비어있습니다.")
    @Size(min = 8, max = 20, message = "사용자 비밀번호는 최소 8글자 이상, 최대 20글자 이하로 작성해주세요.")
    @Pattern(regexp = "^[a-zA-Z0-9]*$", message = "사용자 비밀번호는 알파벳 대소문자(a~z, A~Z), 숫자(0~9)만 입력 가능합니다.")
    private String password;
}
