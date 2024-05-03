package org.mjulikelion.week3assignment.memo.repository;

import org.mjulikelion.week3assignment.memo.entity.Memo;
import org.springframework.stereotype.Repository;

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
    public Memo getMemoByMemoId(String memoId, String userId) {
        Memo memo = memos.get(memoId);
        return memo;
    }

    @Override
    public void updateMemoByMemoId(String userId, String memoId, String newTitle, String newContent) {
        Memo memo = memos.get(memoId);
        memo.setTitle(newTitle);
        memo.setContent(newContent);
    }

    @Override
    public void deleteMemoByMemoId(String userId, String memoId) {
        Memo memo = memos.get(memoId);
        memos.remove(memo);
    }
}
