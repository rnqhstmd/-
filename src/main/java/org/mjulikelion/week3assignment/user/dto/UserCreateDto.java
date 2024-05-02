package org.mjulikelion.week3assignment.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserCreateDto {
    private String userId;
    private String name;
}
