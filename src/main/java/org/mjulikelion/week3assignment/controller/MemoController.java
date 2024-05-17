package org.mjulikelion.week3assignment.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mjulikelion.week3assignment.authentication.AuthenticatedUser;
import org.mjulikelion.week3assignment.dto.requset.memo.MemoCreateDto;
import org.mjulikelion.week3assignment.dto.requset.memo.MemoUpdateDto;
import org.mjulikelion.week3assignment.dto.response.ResponseDto;
import org.mjulikelion.week3assignment.dto.response.memo.LikeListResponseData;
import org.mjulikelion.week3assignment.dto.response.memo.MemoListResponseData;
import org.mjulikelion.week3assignment.dto.response.memo.MemoResponseData;
import org.mjulikelion.week3assignment.model.User;
import org.mjulikelion.week3assignment.service.MemoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/memos")
@Slf4j
public class MemoController {

    private final MemoService memoService;

    // 메모 생성
    @PostMapping
    public ResponseEntity<ResponseDto<Void>> createMemo(@AuthenticatedUser User user,
                                                        @RequestBody @Valid MemoCreateDto memoCreateDto) {
        memoService.createMemo(user, memoCreateDto);
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.CREATED, "메모 생성 완료"), HttpStatus.CREATED);
    }

    // 모든 메모 조회
    @GetMapping
    public ResponseEntity<ResponseDto<MemoListResponseData>> getAllMemosByUserId(@AuthenticatedUser User user) {
        MemoListResponseData memos = memoService.getAllMemosByWriterId(user.getId());
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.OK, "메모 조회 완료", memos), HttpStatus.OK);
    }

    // 특정 메모 조회
    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto<MemoResponseData>> getMemoByMemoId(@PathVariable("id") UUID memoId) {
        MemoResponseData memoResponseData = memoService.getMemoByMemoId(memoId);
        return ResponseEntity.ok(ResponseDto.res(HttpStatus.OK, "메모 조회 완료", memoResponseData));
    }

    // 메모 수정
    @PatchMapping("/{id}")
    public ResponseEntity<ResponseDto<Void>> updateMemoByMemoId(@AuthenticatedUser User user,
                                                                @PathVariable("id") UUID memoId,
                                                                @RequestBody @Valid MemoUpdateDto memoUpdateDto) {
        memoService.updateMemoByMemoId(user.getId(), memoId, memoUpdateDto);
        return ResponseEntity.ok(ResponseDto.res(HttpStatus.OK, "메모 수정 성공"));
    }

    // 메모 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto<Void>> deleteMemoByMemoId(@AuthenticatedUser User user,
                                                                @PathVariable("id") UUID memoId) {
        memoService.deleteMemoByMemoId(user.getId(), memoId);
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.OK, "메모 삭제 완료"), HttpStatus.OK);
    }

    // 메모 좋아요
    @PostMapping("/{id}/likes")
    public ResponseEntity<ResponseDto<Void>> like(@AuthenticatedUser User user,
                                                  @PathVariable("id") UUID memoId) {
        memoService.like(user, memoId);
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.CREATED, "좋아요 생성 완료"), HttpStatus.CREATED);
    }

    // 메모 좋아요 삭제
    @DeleteMapping("/{id}/likes")
    public ResponseEntity<ResponseDto<Void>> deleteLike(@AuthenticatedUser User user,
                                                        @PathVariable("id") UUID memoId) {
        memoService.unlike(user, memoId);
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.OK, "좋아요 삭제 완료"), HttpStatus.ACCEPTED);
    }

    // 좋아요한 유저정보(id, email)와 총 좋아요 수 조회
    @GetMapping("/{id}/likes")
    public ResponseEntity<ResponseDto<LikeListResponseData>> getAllLikesInfo(@PathVariable("id") UUID memoId) {
        LikeListResponseData likeListResponseData = memoService.getAllLikesInfo(memoId);
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.OK, "좋아요 목록 조회 성공", likeListResponseData), HttpStatus.OK);
    }


}
