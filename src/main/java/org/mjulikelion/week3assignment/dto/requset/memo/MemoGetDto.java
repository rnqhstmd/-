package org.mjulikelion.week3assignment.dto.requset.memo;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemoGetDto {
    @NotNull(message = "title이 Null입니다.")
    private final String title;
    @NotNull(message = "content가 Null입니다.")
    private final String content;
}
