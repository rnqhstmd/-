package org.mjulikelion.week3assignment.dto.requset.organization;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class OrganizationCreateDto {
    @NotNull(message = "소속 이름이 비어있습니다.")
    @Pattern(regexp = "^[ㄱ-ㅎ가-힣a-z0-9-_]{2,20}$", message = "소속 이름은 특수문자를 제외한 2~20자리여야 합니다.")
    private final String name;
    @NotNull(message = "소속 소개가 비어있습니다.")
    @Size(min = 2, max = 50, message = "소속 소개는 최소 2글자 이상, 50글자 이하로 작성해주세요.")
    private final String introduction;

    public OrganizationCreateDto(String name, String introduction) {
        this.name = name;
        this.introduction = introduction;
    }
}
