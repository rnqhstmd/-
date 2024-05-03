package org.mjulikelion.week3assignment.user.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserUpdateDto {
    @NotNull(message = "newUserId가 Null입니다.")
    private String newUserId;
    @NotNull(message = "newName이 Null입니다.")
    private String newName;
}
