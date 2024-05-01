package org.mjulikelion.week3assignment.domain.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class MemoUpdateDto {

    @NotNull(message = "newConten가 null입니다.")
    private final String newContent;

    public MemoUpdateDto(String newContent) {
        this.newContent = newContent;
    }
}
