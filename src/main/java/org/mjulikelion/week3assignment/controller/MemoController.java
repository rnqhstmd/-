package org.mjulikelion.week3assignment.controller;

import lombok.AllArgsConstructor;
import org.mjulikelion.week3assignment.domain.Memo;
import org.mjulikelion.week3assignment.sevice.MemoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/memos")
public class MemoController {

    private final MemoService memoService;

    @PostMapping
    public void createMemo(@RequestBody Memo memo) {
        memoService.createMemo(memo.getUserId(), memo.getMemoId(), memo.getContent());
    }

    @GetMapping
    public List<Memo> getAllMemosByUserId(@RequestHeader String userId) {
        return memoService.getAllMemosByUserId(userId);
    }

    @GetMapping("{memoId}")
    public Memo getMemoByUserId(@PathVariable String memoId, @RequestHeader String userId) {
        return memoService.getMemoByUserId(memoId, userId);
    }


    @PatchMapping("/{memoId}")
    public void updateMemoByMemoId(@RequestHeader String userId, @PathVariable String memoId, @RequestBody String newContent) {
        memoService.updateMemoByMemoId(userId, memoId, newContent);
    }

    @DeleteMapping("/{memoId}")
    public void deleteMemoByMemoId(@PathVariable String userId, @PathVariable String memoId) {
        memoService.deleteMemoByMemoId(userId, memoId);
    }
}
