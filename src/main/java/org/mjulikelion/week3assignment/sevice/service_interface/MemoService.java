package org.mjulikelion.week3assignment.sevice.service_interface;

import org.mjulikelion.week3assignment.domain.Memo;
import org.springframework.stereotype.Service;

import java.util.List;


public interface MemoService {
    void createMemo(String userId, String memoId, String content);
    List<Memo> getAllMemosByUserId(String userId);
    Memo getMemoByUserId(String memoId, String userId);
    void updateMemoByMemoId(String userId, String memoId, String newContent);
    void deleteMemoByMemoId(String userId, String memoId);
}
