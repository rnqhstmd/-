package org.mjulikelion.week3assignment.dto.requset.memo;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class MemoCreateDto {
    // 이렇게 DTO 클래스를 만들어서 각종 옵션들을 적용해줄 수 있다.
    @NotNull(message = "메모 제목이 비어있습니다.")
    @Size(min = 8, max = 15, message = "메모 제목은 최소 8글자 이상, 15글자 이하로 작성해주세요.")
    private String title;
    @NotNull(message = "메모가 비어있습니다.")
    @Size(min = 1, max = 100, message = "메모는 최소 1글자 이상, 100글자 이하로 작성해주세요.")
    private String content;
}
