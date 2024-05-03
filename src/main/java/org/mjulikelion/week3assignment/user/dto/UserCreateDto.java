package org.mjulikelion.week3assignment.user.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserCreateDto {
    @NotNull(message = "userId가 Null입니다.")
    private String userId;
    @NotNull(message = "name이 Null입니다.")
    private String name;
}
