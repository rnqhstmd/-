package org.mjulikelion.week3assignment.memo.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mjulikelion.week3assignment.memo.dto.*;
import org.mjulikelion.week3assignment.memo.service.MemoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/memos")
@Slf4j
public class MemoController {

    private final MemoService memoService;

    @PostMapping
    public ResponseEntity<MemoResponseDto<Void>> createMemo(@RequestBody @Valid MemoCreateDto memoCreateDto, @RequestHeader String userId) {
        memoService.createMemo(userId, memoCreateDto);
        return new ResponseEntity<>(MemoResponseDto.res(HttpStatus.CREATED, "메모 생성 완료"), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<MemoResponseDto<MemoResponseData>> getAllMemosByUserId(@RequestHeader String userId) {
        MemoResponseData memos = memoService.getAllMemosByUserId(userId);
        return new ResponseEntity(MemoResponseDto.res(HttpStatus.OK, "메모 조회 완료", memos), HttpStatus.OK);
    }

    @GetMapping("{memoId}")
    public ResponseEntity<MemoResponseDto<MemoGetDto>> getMemoByMemoId(@PathVariable String memoId, @RequestHeader String userId) {
        MemoGetDto memo = memoService.getMemoByUserId(memoId, userId);
        return new ResponseEntity<>(MemoResponseDto.res(HttpStatus.OK, "메모 조회 완료", memo), HttpStatus.OK);
    }


    @PatchMapping("/{memoId}")
    public ResponseEntity<MemoResponseDto<MemoUpdateDto>> updateMemoByMemoId(@RequestHeader String userId, @PathVariable String memoId, @RequestBody @Valid MemoUpdateDto memoUpdateDto) {
        MemoUpdateDto memo = memoService.updateMemoByMemoId(userId, memoId, memoUpdateDto);
        return ResponseEntity.ok(MemoResponseDto.res(HttpStatus.OK, "메모 수정 완료", memo));
    }

    @DeleteMapping("/{memoId}")
    public ResponseEntity<MemoResponseDto<Void>> deleteMemoByMemoId(@RequestHeader String userId, @PathVariable String memoId) {
        memoService.deleteMemoByMemoId(userId, memoId);
        return new ResponseEntity<>(MemoResponseDto.res(HttpStatus.OK, "메모 삭제 완료"), HttpStatus.OK);
    }
}
