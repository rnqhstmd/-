package org.mjulikelion.week3assignment.dto.response.memo;

import lombok.Getter;
import org.mjulikelion.week3assignment.model.Memo;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class MemoListResponseData {
    private final List<MemoResponse> memoList;
    private final int total;

    public MemoListResponseData(List<Memo> memos) {
        this.memoList = memos.stream()
                .map(memo -> new MemoResponse(memo))
                .collect(Collectors.toList());
        this.total = memoList.size();
    }
}
