package org.mjulikelion.week3assignment.like.repository;

import org.mjulikelion.week3assignment.like.entity.Like;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class LikeRepositoryImpl implements LikeRepository {

    private final Map<String, Like> likes = new HashMap<>();

    @Override
    public void create(Like like) {
        likes.put(like.getLikeId(), like);
    }

    @Override
    public void delete(Like like) {
        likes.remove(like.getLikeId());
    }

    @Override
    public List<Like> findAllNameByMemoId(String memoId) {
        return likes.values().stream()
                .filter(like -> like.getMemoId().equals(memoId))
                .collect(Collectors.toList());
    }

    // anymatch 사용으로 like 객체가 존재하는지 boolean 값 리턴
    @Override
    public boolean likeExistsByUserIdAndMemoId(String userId, String memoId) {
        return likes.values().stream()
                .anyMatch(like -> like.getUserId().equals(userId) && like.getMemoId().equals(memoId));
    }
}
