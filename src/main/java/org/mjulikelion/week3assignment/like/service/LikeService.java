package org.mjulikelion.week3assignment.like.service;

import lombok.AllArgsConstructor;
import org.mjulikelion.week3assignment.exception.LikeNotFoundException;
import org.mjulikelion.week3assignment.exception.MemoNotFoundException;
import org.mjulikelion.week3assignment.exception.UserAlreadyExists;
import org.mjulikelion.week3assignment.exception.errorcode.ErrorCode;
import org.mjulikelion.week3assignment.like.dto.LikeGetDto;
import org.mjulikelion.week3assignment.like.dto.LikeRequestDto;
import org.mjulikelion.week3assignment.like.entity.Like;
import org.mjulikelion.week3assignment.like.repository.LikeRepository;
import org.mjulikelion.week3assignment.memo.repository.MemoRepository;
import org.mjulikelion.week3assignment.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class LikeService {
    private final LikeRepository likeRepository;
    private final UserRepository userRepository;
    private final MemoRepository memoRepository;

    public void createLike(LikeRequestDto likeRequestDto) {
        if (likeRepository.likeExistsByUserIdAndMemoId(likeRequestDto.getUserId(), likeRequestDto.getMemoId())) {
            throw new UserAlreadyExists(ErrorCode.USER_ALREADY_EXISTS);
        }
        Like like = new Like(likeRequestDto.getUserId(), likeRequestDto.getMemoId());
        likeRepository.create(like);
    }

    public void deleteLike(LikeRequestDto likeRequestDto) {
        if (!likeRepository.likeExistsByUserIdAndMemoId(likeRequestDto.getUserId(), likeRequestDto.getMemoId())) {
            throw new LikeNotFoundException(ErrorCode.LIKE_NOT_FOUND);
        }
        Like like = likeRepository.findLikeByUserIdAndMemoId(likeRequestDto.getUserId(), likeRequestDto.getMemoId());
        if (like == null) {
            throw new LikeNotFoundException((ErrorCode.LIKE_NOT_FOUND));
        }
        likeRepository.delete(like);
    }

    public LikeGetDto getLikesInfo(String memoId) {
        if (!memoRepository.memoIdExists(memoId)) {
            throw new MemoNotFoundException(ErrorCode.MEMO_NOT_FOUND, "좋아요 정보를 memoId로 불러오려했으나, 존재하지 않습니다.");
        }

        List<Like> likes = likeRepository.findAllLikes(memoId);
        List<String> userNames = likes.stream()
                .map(like -> userRepository.findByUserId(like.getUserId()).getName())
                .collect(Collectors.toList());
        List<LocalDateTime> likeTime = likes.stream()
                .map(Like::getLikeTime)
                .collect(Collectors.toList());

        return new LikeGetDto(likeTime, likes.size(), userNames);
    }
}
