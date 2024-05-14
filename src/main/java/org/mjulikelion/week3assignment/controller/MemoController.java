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

    @GetMapping("/test")
    public ResponseEntity<ResponseDto<Void>> test() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.OK, "ok"), HttpStatus.OK);
    }


    @PostMapping
    public ResponseEntity<ResponseDto<Void>> createMemo(@AuthenticatedUser User user,
                                                        @RequestBody @Valid MemoCreateDto memoCreateDto) {
        memoService.createMemo(user.getId(), memoCreateDto);
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.CREATED, "메모 생성 완료"), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<ResponseDto<MemoListResponseData>> getAllMemosByUserId(@AuthenticatedUser User user) {
        MemoListResponseData memos = memoService.getAllMemosByWriterId(user.getId());
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.OK, "메모 조회 완료", memos), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto<MemoResponseData>> getMemoByMemoId(@PathVariable("id") UUID memoId) {
        MemoResponseData memoResponseData = memoService.getMemoByMemoId(memoId);
        return ResponseEntity.ok(ResponseDto.res(HttpStatus.OK, "메모 조회 완료", memoResponseData));
    }


    @PatchMapping("/{id}")
    public ResponseEntity<ResponseDto<Void>> updateMemoByMemoId(@AuthenticatedUser User user,
                                                                @PathVariable("id") UUID memoId,
                                                                @RequestBody @Valid MemoUpdateDto memoUpdateDto) {
        memoService.updateMemoByMemoId(user.getId(), memoId, memoUpdateDto);
        return ResponseEntity.ok(ResponseDto.res(HttpStatus.OK, "메모 수정 성공"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto<Void>> deleteMemoByMemoId(@AuthenticatedUser User user,
                                                                @PathVariable("id") UUID memoId) {
        memoService.deleteMemoByMemoId(user.getId(), memoId);
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.OK, "메모 삭제 완료"), HttpStatus.OK);
    }

    @PostMapping("/{id}/likes")
    public ResponseEntity<ResponseDto<Void>> like(@AuthenticatedUser User user,
                                                  @PathVariable("id") UUID memoId) {
        memoService.like(memoId, user.getId());
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.CREATED, "좋아요 생성 완료"), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}/likes")
    public ResponseEntity<ResponseDto<Void>> deleteLike(@AuthenticatedUser User user,
                                                        @PathVariable("id") UUID memoId) {
        memoService.unlike(memoId, user.getId());
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.OK, "좋아요 삭제 완료"), HttpStatus.ACCEPTED);
    }

    @GetMapping("/{id}/likes")
    public ResponseEntity<ResponseDto<LikeListResponseData>> getAllLikesInfo(@PathVariable("id") UUID memoId) {
        LikeListResponseData likeListResponseData = memoService.getAllLikesInfo(memoId);
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.OK, "좋아요 목록 조회 성공", likeListResponseData), HttpStatus.OK);
    }


}
