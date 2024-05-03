package org.mjulikelion.week3assignment.like.entity;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;


@Getter
public class Like {

    private final String userId;
    private final String memoId;
    private final String likeId;
    private final LocalDateTime likeTime; // 좋아요 누른 시각

    public Like(String userId, String memoId) {
        this.userId = userId;
        this.memoId = memoId;
        this.likeId = UUID.randomUUID().toString();
        this.likeTime = LocalDateTime.now(); // likeTime = 현재 시간
    }
}
