package org.mjulikelion.week3assignment.dto.response.memo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.mjulikelion.week3assignment.model.Memo;

@Getter
@Builder
@AllArgsConstructor
public class MemoResponse {
    private String id;
    private String title;
    private String content;

    public MemoResponse(Memo memo) {
        this.id = String.valueOf(memo.getId());
        this.title = memo.getTitle();
        this.content = memo.getContent();
    }
}
