package org.mjulikelion.week3assignment.memo.service;

import lombok.AllArgsConstructor;
import org.mjulikelion.week3assignment.exception.MemoNotFoundException;
import org.mjulikelion.week3assignment.exception.MemoNotMatchException;
import org.mjulikelion.week3assignment.exception.UserNotMatchException;
import org.mjulikelion.week3assignment.exception.errorcode.ErrorCode;
import org.mjulikelion.week3assignment.memo.dto.MemoCreateDto;
import org.mjulikelion.week3assignment.memo.dto.MemoGetDto;
import org.mjulikelion.week3assignment.memo.dto.MemoResponseData;
import org.mjulikelion.week3assignment.memo.dto.MemoUpdateDto;
import org.mjulikelion.week3assignment.memo.entity.Memo;
import org.mjulikelion.week3assignment.memo.repository.MemoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class MemoService {

    private final MemoRepository memoRepository;

    public void createMemo(String userId, MemoCreateDto memoCreateDto) {
        String memoId = UUID.randomUUID().toString();
        Memo memo = new Memo(memoId, userId, memoCreateDto.getTitle(), memoCreateDto.getContent());
        memoRepository.create(memo);
    }

    public MemoResponseData getAllMemosByUserId(String userId) {
        List<Memo> memos = memoRepository.getAllMemoByUserId(userId);
        if (memos.isEmpty()) {
            throw new MemoNotFoundException(ErrorCode.MEMO_NOT_FOUND, "모든 메모를 가져오려했으나, 존재하지 않습니다.");
        }
        int total = memos.size();
        MemoResponseData memoTestResponseData = MemoResponseData.builder()
                .memoList(memos)
                .total(total)
                .build();
        return memoTestResponseData;
    }


    public MemoGetDto getMemoByUserId(String memoId, String userId) {
        if (!memoRepository.memoIdExists(memoId)) {
            throw new MemoNotFoundException(ErrorCode.MEMO_NOT_FOUND, "특정 메모를 가져오려했으나, 존재하지 않습니다.");
        }

        Memo memo = memoRepository.getMemoByMemoId(memoId, userId);
        return new MemoGetDto(memo.getTitle(), memo.getContent());
    }


    public MemoUpdateDto updateMemoByMemoId(String userId, String memoId, MemoUpdateDto memoUpdateDto) {
        if (!memoRepository.memoIdExists(memoId)) {
            throw new MemoNotFoundException(ErrorCode.MEMO_NOT_FOUND, "특정 메모를 가져오려했으나, 존재하지 않습니다.");
        }

        Memo memo = memoRepository.getMemoByMemoId(memoId, userId);
        if (!memo.getUserId().equals(userId)) {
            throw new UserNotMatchException(ErrorCode.USER_NOT_MATCH, "특정 메모를 수정하려했으나, userId가 일치하지 않습니다.");
        } else if (!memo.getMemoId().equals(memoId)) {
            throw new MemoNotMatchException(ErrorCode.MEMO_NOT_MATCH, "특정 메모를 수정하려했으나, memoId가 일치하지 않습니다.");
        }

        memo.setTitle(memoUpdateDto.getNewTitle());
        memo.setContent(memoUpdateDto.getNewContent()); // newContent 로 세팅
        memoRepository.updateMemoByMemoId(userId, memoId, memo.getTitle(), memo.getContent()); // newContent 로 대체
        return new MemoUpdateDto(memo.getTitle(), memo.getContent());
    }


    public void deleteMemoByMemoId(String userId, String memoId) {

        if (!memoRepository.memoIdExists(memoId)) {
            throw new MemoNotFoundException(ErrorCode.MEMO_NOT_FOUND, "특정 메모를 가져오려했으나, 존재하지 않습니다.");
        }

        Memo memo = memoRepository.getMemoByMemoId(memoId, userId);
        if (!memo.getUserId().equals(userId)) {
            throw new UserNotMatchException(ErrorCode.USER_NOT_MATCH, "특정 메모를 삭제하려했으나, userId가 일치하지 않습니다.");
        } else if (!memo.getMemoId().equals(memoId)) {
            throw new MemoNotMatchException(ErrorCode.MEMO_NOT_MATCH, "특정 메모를 삭제하려했으나, memoId가 일치하지 않습니다.");
        }
        memoRepository.deleteMemoByMemoId(userId, memoId);
    }
}
