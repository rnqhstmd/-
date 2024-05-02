package org.mjulikelion.week3assignment.like.controller;

import lombok.AllArgsConstructor;
import org.mjulikelion.week3assignment.like.dto.LikeRequestDto;
import org.mjulikelion.week3assignment.like.dto.LikeResponseDto;
import org.mjulikelion.week3assignment.like.service.LikeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/memos/{memoId}/likes")
public class LikeController {

    private final LikeService likeService;

    @PostMapping
    public ResponseEntity<LikeResponseDto<Void>> createLike(@RequestBody LikeRequestDto likeRequestDto) {
        likeService.createLike(likeRequestDto);
        return new ResponseEntity<>(LikeResponseDto.res(HttpStatus.CREATED, "좋아요 생성 완료"), HttpStatus.CREATED);
    }

    @DeleteMapping
    public ResponseEntity<LikeResponseDto<Void>> deleteLike(@RequestBody LikeRequestDto likeRequestDto) {
        likeService.deleteLike(likeRequestDto);
        return new ResponseEntity<>(LikeResponseDto.res(HttpStatus.ACCEPTED, "좋아요 삭제 완료"), HttpStatus.ACCEPTED);
    }
}
