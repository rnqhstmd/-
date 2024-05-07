package org.mjulikelion.week3assignment.dto.response.memo;

import lombok.Builder;
import lombok.Getter;
import org.mjulikelion.week3assignment.model.MemoLike;

import java.util.List;

@Getter
@Builder
public class LikeListResponseData {
    private List<MemoLike> likeList;
    private int likeCount;
}
