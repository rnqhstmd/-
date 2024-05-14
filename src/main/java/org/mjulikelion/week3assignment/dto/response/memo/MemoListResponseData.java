package org.mjulikelion.week3assignment.dto.response.memo;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class MemoListResponseData {
    private List<MemoResponse> memoList;
    private int total;
}
