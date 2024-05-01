package org.mjulikelion.week3assignment.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mjulikelion.week3assignment.domain.dto.MemoCreateDto;
import org.mjulikelion.week3assignment.domain.dto.MemoGetDto;
import org.mjulikelion.week3assignment.domain.dto.MemoResponseData;
import org.mjulikelion.week3assignment.domain.dto.MemoUpdateDto;
import org.mjulikelion.week3assignment.domain.dto.response.ResponseDto;
import org.mjulikelion.week3assignment.sevice.MemoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/memos")
@Slf4j
public class MemoController {

    private final MemoService memoService;

//    @GetMapping("/test")
//    public ResponseEntity<ResponseDto<Void>> test() {
//        throw new UserNotFoundException(ErrorCode.USER_NOT_FOUND);
//        //return new ResponseEntity<>(ResponseDto.res(HttpStatus.ACCEPTED, "굿"), HttpStatus.ACCEPTED);
//    }
//
//    @GetMapping("/test1")
//    public ResponseEntity<ResponseDto<Void>> test1() {
//        throw new MemoNotFoundException(ErrorCode.USER_NOT_FOUND, "test");
//        //return new ResponseEntity<>(ResponseDto.res(HttpStatus.ACCEPTED, "굿"), HttpStatus.ACCEPTED);
//    }

    @PostMapping
    public ResponseEntity<ResponseDto<Void>> createMemo(@RequestBody @Valid MemoCreateDto memoCreateDto, @RequestHeader String userId) {
        memoService.createMemo(userId, memoCreateDto);
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.CREATED, "생성하였습니다."), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<ResponseDto<MemoResponseData>> getAllMemosByUserId(@RequestHeader String userId) {
        MemoResponseData memos = memoService.getAllMemosByUserId(userId);
        return new ResponseEntity(ResponseDto.res(HttpStatus.OK, "반환하였습니다.", memos), HttpStatus.OK);
    }

    @GetMapping("{memoId}")
    public ResponseEntity<ResponseDto<MemoGetDto>> getMemoByMemoId(@PathVariable String memoId, @RequestHeader String userId) {
        MemoGetDto memo = memoService.getMemoByUserId(memoId, userId);
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.OK, "반환하였습니다.", memo), HttpStatus.OK);
    }


    @PatchMapping("/{memoId}")
    public ResponseEntity<ResponseDto<MemoUpdateDto>> updateMemoByMemoId(@RequestHeader String userId, @PathVariable String memoId, @RequestBody @Valid MemoUpdateDto memoUpdateDto) {
        MemoUpdateDto memo = memoService.updateMemoByMemoId(userId, memoId, memoUpdateDto);
        return ResponseEntity.ok(ResponseDto.res(HttpStatus.OK, "수정되었습니다.", memo));
    }

    @DeleteMapping("/{memoId}")
    public ResponseEntity<ResponseDto<Void>> deleteMemoByMemoId(@RequestHeader String userId, @PathVariable String memoId) {
        memoService.deleteMemoByMemoId(userId, memoId);
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.OK, "삭제되었습니다."), HttpStatus.OK);
    }
}
