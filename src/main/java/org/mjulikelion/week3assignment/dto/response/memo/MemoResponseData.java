package org.mjulikelion.week3assignment.dto.response.memo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.mjulikelion.week3assignment.model.Memo;

@Getter
@Builder
@AllArgsConstructor
public class MemoResponseData {
    private final MemoResponse memo;
    private final int likeCount;

    public MemoResponseData(Memo memo) {
        this.memo = new MemoResponse(memo);
        this.likeCount = memo.getMemoLikes().size();
    }
}
