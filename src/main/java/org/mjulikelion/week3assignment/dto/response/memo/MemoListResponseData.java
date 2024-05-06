package org.mjulikelion.week3assignment.dto.response.memo;

import lombok.Builder;
import lombok.Getter;
import org.mjulikelion.week3assignment.model.Memo;

import java.util.List;

@Getter
@Builder
public class MemoListResponseData {
    private List<Memo> memoList;
    private int total;
}
