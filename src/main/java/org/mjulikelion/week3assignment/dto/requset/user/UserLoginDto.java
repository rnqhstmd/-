package org.mjulikelion.week3assignment.dto.requset.user;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserLoginDto {
    @NotNull(message = "email이 null입니다.")
    private String email;
    @NotNull(message = "password가 null입니다.")
    private String password;
}
