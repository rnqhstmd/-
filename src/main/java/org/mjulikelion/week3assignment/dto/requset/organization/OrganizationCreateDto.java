package org.mjulikelion.week3assignment.dto.requset.organization;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class OrganizationCreateDto {
    @NotNull(message = "name이 null입니다.")
    private final String name;
    @NotNull(message = "introduction이 null입니다.")
    private final String introduction;

    public OrganizationCreateDto(String name, String introduction) {
        this.name = name;
        this.introduction = introduction;
    }
}
