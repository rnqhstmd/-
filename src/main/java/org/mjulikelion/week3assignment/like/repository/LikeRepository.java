package org.mjulikelion.week3assignment.like.repository;

import org.mjulikelion.week3assignment.like.entity.Like;

import java.util.List;

public interface LikeRepository {
    void create(Like like);

    void delete(Like like);

    List<Like> findAllLikes(String memoId);

    boolean likeExistsByUserIdAndMemoId(String userId, String memoId);

    Like findLikeByUserIdAndMemoId(String userId, String memoId);
}
