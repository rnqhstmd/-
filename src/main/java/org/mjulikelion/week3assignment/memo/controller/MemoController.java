package org.mjulikelion.week3assignment.memo.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mjulikelion.week3assignment.like.dto.LikeGetDto;
import org.mjulikelion.week3assignment.like.dto.LikeRequestDto;
import org.mjulikelion.week3assignment.like.service.LikeService;
import org.mjulikelion.week3assignment.memo.dto.MemoCreateDto;
import org.mjulikelion.week3assignment.memo.dto.MemoGetDto;
import org.mjulikelion.week3assignment.memo.dto.MemoResponseData;
import org.mjulikelion.week3assignment.memo.dto.MemoUpdateDto;
import org.mjulikelion.week3assignment.memo.service.MemoService;
import org.mjulikelion.week3assignment.response.ResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/memos")
@Slf4j
public class MemoController {

    private final MemoService memoService;
    private final LikeService likeService;

    @PostMapping
    public ResponseEntity<ResponseDto<Void>> createMemo(@RequestBody @Valid MemoCreateDto memoCreateDto, @RequestHeader String userId) {
        memoService.createMemo(userId, memoCreateDto);
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.CREATED, "메모 생성 완료"), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<ResponseDto<MemoResponseData>> getAllMemosByUserId(@RequestHeader String userId) {
        MemoResponseData memos = memoService.getAllMemosByUserId(userId);
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.OK, "메모 조회 완료", memos), HttpStatus.OK);
    }

    @GetMapping("{memoId}")
    public ResponseEntity<ResponseDto<MemoGetDto>> getMemoByMemoId(@PathVariable String memoId, @RequestHeader String userId) {
        MemoGetDto memo = memoService.getMemoByUserId(memoId, userId);
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.OK, "메모 조회 완료", memo), HttpStatus.OK);
    }


    @PatchMapping("/{memoId}")
    public ResponseEntity<ResponseDto<MemoUpdateDto>> updateMemoByMemoId(@RequestHeader String userId, @PathVariable String memoId, @RequestBody @Valid MemoUpdateDto memoUpdateDto) {
        MemoUpdateDto memo = memoService.updateMemoByMemoId(userId, memoId, memoUpdateDto);
        return ResponseEntity.ok(ResponseDto.res(HttpStatus.OK, "메모 수정 완료", memo));
    }

    @DeleteMapping("/{memoId}")
    public ResponseEntity<ResponseDto<Void>> deleteMemoByMemoId(@RequestHeader String userId, @PathVariable String memoId) {
        memoService.deleteMemoByMemoId(userId, memoId);
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.OK, "메모 삭제 완료"), HttpStatus.OK);
    }

    @PostMapping("/{memoId}/likes")
    public ResponseEntity<ResponseDto<Void>> createLike(@RequestBody LikeRequestDto likeRequestDto) {
        likeService.createLike(likeRequestDto);
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.CREATED, "좋아요 생성 완료"), HttpStatus.CREATED);
    }

    @GetMapping("/{memoId}/likes")
    public ResponseEntity<ResponseDto<LikeGetDto>> getAllLikeInfo(@PathVariable String memoId) {
        likeService.getLikesInfo(memoId);
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.OK, "메모 정보 가져오기 완료"), HttpStatus.OK);
    }

    @DeleteMapping("/{memoId}/likes")
    public ResponseEntity<ResponseDto<Void>> deleteLike(@RequestBody LikeRequestDto likeRequestDto) {
        likeService.deleteLike(likeRequestDto);
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.ACCEPTED, "좋아요 삭제 완료"), HttpStatus.ACCEPTED);
    }
}
