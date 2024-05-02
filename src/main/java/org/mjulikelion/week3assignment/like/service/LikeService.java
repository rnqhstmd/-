package org.mjulikelion.week3assignment.like.service;

import lombok.AllArgsConstructor;
import org.mjulikelion.week3assignment.like.dto.LikeGetDto;
import org.mjulikelion.week3assignment.like.dto.LikeRequestDto;
import org.mjulikelion.week3assignment.like.entity.Like;
import org.mjulikelion.week3assignment.like.repository.LikeRepository;
import org.mjulikelion.week3assignment.memo.repository.MemoRepository;
import org.mjulikelion.week3assignment.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class LikeService {
    private final LikeRepository likeRepository;
    private final UserRepository userRepository;
    private final MemoRepository memoRepository;

    public void createLike(LikeRequestDto likeRequestDto) {
        Like like = new Like(likeRequestDto.getUserId(), likeRequestDto.getMemoId());
        likeRepository.create(like);
    }

    public void deleteLike(LikeRequestDto likeRequestDto) {
        List<Like> likes = likeRepository.findAllNameByMemoId(likeRequestDto.getMemoId());
        List<String> userNames = likes.stream()
                .map(like -> userRepository.findByUserId(like.getUserId()).getName())
                .collect(Collectors.toList());
        return;
        new LikeGetDto(likes.size(), userNames);
    }
}
