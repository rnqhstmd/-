package org.mjulikelion.week3assignment.repository;

import org.mjulikelion.week3assignment.model.MemoLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface LikeRepository extends JpaRepository<MemoLike, Long> {

    List<MemoLike> findAllLikes(UUID memoId);

    boolean likeExistsByUserIdAndMemoId(String userId, String memoId);

    MemoLike findLikeByUserIdAndMemoId(String userId, String memoId);
}
