package org.mjulikelion.week3assignment.like.entity;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;


@Getter
@Builder
public class Like {

    private final String userId;
    private final String memoId;
    private final LocalDateTime likeTime; // 좋아요 누른 시각
    private String likeId;

    @Builder
    public Like(String userId, String memoId) {
        this.userId = userId;
        this.memoId = memoId;
        this.likeId = UUID.randomUUID().toString();
        this.likeTime = LocalDateTime.now(); // likeTime = 현재 시간
    }
}
