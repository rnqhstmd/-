package org.mjulikelion.week3assignment.memo.repository;

import org.mjulikelion.week3assignment.memo.entity.Memo;

import java.util.List;

public interface MemoRepository {

    void create(Memo memo);

    //메모 아이디 중복 확인
    boolean memoIdExists(String memoId);

    //특정 사용자의 모든 메모 조회
    List<Memo> getAllMemoByUserId(String userId);

    //특정 사용자의 특정 메모 조회
    Memo getMemoByMemoId(String memoId, String userId);

    void updateMemoByMemoId(String userId, String memoId, String newTitle, String newContent);

    void deleteMemoByMemoId(String userId, String memoId);
}
