package org.mjulikelion.week3assignment.dto.requset.user;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserUpdateDto {
    @NotNull(message = "newUserId가 Null입니다.")
    private String newEmail;
    @NotNull(message = "newName이 Null입니다.")
    private String newName;
}
