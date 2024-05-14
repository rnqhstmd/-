package org.mjulikelion.week3assignment.dto.response.memo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class MemoResponseData {
    private final MemoResponse memo;
    private final int likeCount;
}
