package org.mjulikelion.week3assignment.domain.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class MemoCreateDto {
    // 이렇게 DTO 클래스를 만들어서 각종 옵션들을 적용해줄 수 있다.
    @NotNull(message = "content가 null입니다.")
    private String content;
}
