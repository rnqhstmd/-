package org.mjulikelion.week3assignment.sevice;

import lombok.AllArgsConstructor;
import org.mjulikelion.week3assignment.domain.Memo;
import org.mjulikelion.week3assignment.repository.repo_interface.MemoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class MemoService {

    private final MemoRepository memoRepository;


    public void createMemo(String userId, String memoId, String content) {
        memoRepository.create(new Memo(memoId, content, userId));
    }

    public List<Memo> getAllMemosByUserId(String userId) {
        return memoRepository.getAllMemoByUserId(userId);
    }


    public Memo getMemoByUserId(String memoId, String userId) {
        return memoRepository.getMemoByUserId(memoId, userId);
    }


    public void updateMemoByMemoId(String userId, String memoId, String newContent) {
        memoRepository.updateMemoByMemoId(userId, memoId, newContent);
    }


    public void deleteMemoByMemoId(String userId, String memoId) {
        memoRepository.deleteMemoByMemoId(userId, memoId);
    }

}
