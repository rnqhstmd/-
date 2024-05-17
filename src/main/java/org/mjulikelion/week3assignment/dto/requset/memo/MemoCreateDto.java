package org.mjulikelion.week3assignment.dto.requset.memo;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

@Getter
@AllArgsConstructor
public class MemoCreateDto {
    @NotNull(message = "메모 제목이 비어있습니다.")
    @Length(min = 8, max = 15, message = "메모 제목은 최소 8글자 이상, 15글자 이하로 작성해주세요.")
    private String title;
    @NotNull(message = "메모가 비어있습니다.")
    @Length(min = 1, max = 100, message = "메모는 최소 1글자 이상, 100글자 이하로 작성해주세요.")
    private String content;
}
