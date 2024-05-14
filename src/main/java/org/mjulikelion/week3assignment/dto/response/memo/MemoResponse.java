package org.mjulikelion.week3assignment.dto.response.memo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class MemoResponse {
    private String id;
    private String title;
    private String content;
}
