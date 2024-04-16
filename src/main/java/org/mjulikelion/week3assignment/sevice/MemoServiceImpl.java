package org.mjulikelion.week3assignment.sevice;

import lombok.AllArgsConstructor;
import org.mjulikelion.week3assignment.domain.Memo;
import org.mjulikelion.week3assignment.repository.repo_interface.MemoRepository;
import org.mjulikelion.week3assignment.sevice.service_interface.MemoService;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@AllArgsConstructor
public class MemoServiceImpl implements MemoService {

    private final MemoRepository memoRepository;

    @Override
    public void createMemo(String userId, String memoId, String content) {
        memoRepository.createMemo(new Memo(memoId, content, userId));
    }

    @Override
    public List<Memo> getAllMemosByUserId(String userId) {
        return memoRepository.getAllMemoByUserId(userId);
    }

    @Override
    public Memo getMemoByUserId(String memoId, String userId) {
        return memoRepository.getMemoByUserId(memoId, userId);
    }

    @Override
    public void updateMemoByMemoId(String userId, String memoId, String newContent) {
        memoRepository.updateMemoByMemoId(userId, memoId, newContent);
    }

    @Override
    public void deleteMemoByMemoId(String userId, String memoId) {
        memoRepository.deleteMemoByMemoId(userId, memoId);
    }

}
