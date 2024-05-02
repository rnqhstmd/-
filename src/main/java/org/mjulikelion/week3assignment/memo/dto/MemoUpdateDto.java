package org.mjulikelion.week3assignment.memo.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class MemoUpdateDto {
    @NotNull(message = "newTitle이 Null입니다.")
    private final String newTitle;

    @NotNull(message = "newConten가 null입니다.")
    private final String newContent;

    public MemoUpdateDto(String newTitle, String newContent) {
        this.newTitle = newTitle;
        this.newContent = newContent;
    }
}
