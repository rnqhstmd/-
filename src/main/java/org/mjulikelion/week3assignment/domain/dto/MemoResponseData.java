package org.mjulikelion.week3assignment.domain.dto;

import lombok.Builder;
import lombok.Getter;
import org.mjulikelion.week3assignment.domain.Memo;

import java.util.List;

@Getter
@Builder
public class MemoResponseData {
    private List<Memo> memoList;
    private int total;
}
