package org.mjulikelion.week3assignment.repository;

import org.mjulikelion.week3assignment.domain.Memo;
import org.mjulikelion.week3assignment.domain.User;
import org.mjulikelion.week3assignment.repository.repo_interface.MemoRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class MemoRepositoryImpl implements MemoRepository {

    private final Map<String, Memo> memos = new HashMap<>();

    @Override
    public void create(Memo memo) {
        memos.put(memo.getMemoId(), memo);
    }

    @Override
    public boolean memoIdExists(String memoId) {
        return memos.containsKey(memoId);
    }

    @Override
    public List<Memo> getAllMemoByUserId(String userId) {
        List<Memo> memoList = memos.values().stream()
                .filter(memo -> memo.getUserId().equals(userId))
                .collect(Collectors.toList());

        return memoList;
    }

    @Override
    public Memo getMemoByUserId(String memoId, String userId) {
        Memo memo = memos.get(memoId);
        if (memo.getUserId().equals(userId)) {
            return memo;
        } else {
            throw new IllegalArgumentException("존재하지 않는 메모입니다.");
        }
    }

    @Override
    public void updateMemoByMemoId(String userId, String memoId, String newContent) {
        Memo memo = memos.get(memoId);
        if (memo.getUserId().equals(userId)) {
            memo.setContent(newContent);
        }
        else{
            throw new IllegalArgumentException("존재하지 않는 메모입니다.");
        }
    }

    @Override
    public void deleteMemoByMemoId(String userId, String memoId) {
        Memo memo = memos.get(memoId);
        if (memo.getUserId().equals(userId)) {
            memos.remove(memoId);
        }
        else{
            throw new IllegalArgumentException("존재하지 않는 메모입니다.");
        }
    }
}
