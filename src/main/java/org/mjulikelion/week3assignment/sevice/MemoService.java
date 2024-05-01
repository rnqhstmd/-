package org.mjulikelion.week3assignment.sevice;

import lombok.AllArgsConstructor;
import org.mjulikelion.week3assignment.domain.Memo;
import org.mjulikelion.week3assignment.domain.dto.MemoCreateDto;
import org.mjulikelion.week3assignment.domain.dto.MemoGetDto;
import org.mjulikelion.week3assignment.domain.dto.MemoResponseData;
import org.mjulikelion.week3assignment.domain.dto.MemoUpdateDto;
import org.mjulikelion.week3assignment.repository.repo_interface.MemoRepository;
import org.mjulikelion.week3assignment.repository.repo_interface.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class MemoService {

    private final MemoRepository memoRepository;
    private final UserRepository userRepository;


    public void createMemo(String userId, MemoCreateDto memoCreateDto) {
        String memoId = UUID.randomUUID().toString();
        Memo memo = new Memo(memoId, userId, memoCreateDto.getContent());
        memoRepository.create(memo);
    }

    public MemoResponseData getAllMemosByUserId(String userId) {
        List<Memo> memos = memoRepository.getAllMemoByUserId(userId);
        int total = memos.size();
        MemoResponseData memoTestResponseData = MemoResponseData.builder()
                .memoList(memos)
                .total(total)
                .build();
        return memoTestResponseData;
    }


    public MemoGetDto getMemoByUserId(String memoId, String userId) {
        Memo memo = memoRepository.getMemoByMemoId(memoId, userId);
        return new MemoGetDto(memo.getContent());
    }


    public MemoUpdateDto updateMemoByMemoId(String userId, String memoId, MemoUpdateDto memoUpdateDto) {
        Memo memo = memoRepository.getMemoByMemoId(memoId, userId);
        memo.setContent(memoUpdateDto.getNewContent()); // newContent 로 세팅
        memoRepository.updateMemoByMemoId(userId, memoId, memo.getContent()); // newContent 로 대체
        return new MemoUpdateDto(memo.getContent());
    }


    public void deleteMemoByMemoId(String userId, String memoId) {
        memoRepository.deleteMemoByMemoId(userId, memoId);
    }

}
