package org.mjulikelion.week3assignment.dto.response.memo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.mjulikelion.week3assignment.model.Memo;

@Getter
@Builder
@AllArgsConstructor
public class MemoResponseDto {
    private final Memo memo;
    private final int likeCount;
}
