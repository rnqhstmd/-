package org.mjulikelion.week3assignment.service;

import lombok.AllArgsConstructor;
import org.mjulikelion.week3assignment.dto.requset.memo.MemoCreateDto;
import org.mjulikelion.week3assignment.dto.requset.memo.MemoUpdateDto;
import org.mjulikelion.week3assignment.dto.response.memo.LikeListResponseData;
import org.mjulikelion.week3assignment.dto.response.memo.MemoListResponseData;
import org.mjulikelion.week3assignment.dto.response.memo.MemoResponseDto;
import org.mjulikelion.week3assignment.exception.MemoNotFoundException;
import org.mjulikelion.week3assignment.exception.MemoNotMatchException;
import org.mjulikelion.week3assignment.exception.UserNotFoundException;
import org.mjulikelion.week3assignment.exception.UserNotMatchException;
import org.mjulikelion.week3assignment.exception.errorcode.ErrorCode;
import org.mjulikelion.week3assignment.model.Memo;
import org.mjulikelion.week3assignment.model.MemoLike;
import org.mjulikelion.week3assignment.model.User;
import org.mjulikelion.week3assignment.repository.MemoRepository;
import org.mjulikelion.week3assignment.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class MemoService {

    private final UserRepository userRepository;
    private final MemoRepository memoRepository;

    private static void validateMemoByWriterId(UUID writerId, Memo memo) {
        if (!memo.getWriterId().equals(writerId)) {
            throw new UserNotMatchException(ErrorCode.USER_NOT_MATCH, "작성자가 일치하지 않습니다.");
        }
    }

    public MemoResponseDto find(UUID memoId) {
        Memo memo = memoRepository.findById(memoId)
                .orElseThrow(() -> new MemoNotFoundException(ErrorCode.MEMO_NOT_FOUND));

        int likeCount = memo.getMemoLikes().size();

        return new MemoResponseDto(memo, likeCount);
    }

    public MemoListResponseData getAllMemosByWriterId(UUID writerId) {
        validateUser(writerId);
        if (memoRepository.findByUserId(writerId).isEmpty()) {
            throw new MemoNotFoundException(ErrorCode.MEMO_NOT_FOUND);
        }
        List<Memo> memoList = memoRepository.findByUserId(writerId);

        MemoListResponseData memoListResponseData = MemoListResponseData.builder()
                .memoList(memoList)
                .total(memoList.size())
                .build();

        return memoListResponseData;
    }

    public void createMemo(UUID writerId, MemoCreateDto memoCreateDto) {
        User user = userRepository.findById(writerId)
                .orElseThrow(() -> new UserNotFoundException(ErrorCode.USER_NOT_FOUND));

        Memo memo = Memo.builder()
                .title(memoCreateDto.getTitle())
                .content(memoCreateDto.getContent())
                .user(user)
                .build();

        memoRepository.save(memo);
    }

    public void updateMemoByMemoId(UUID writerId, UUID memoId, MemoUpdateDto memoUpdateDto) {
        validateUser(writerId);
        Memo memo = memoRepository.findById(memoId).orElseThrow(() -> new MemoNotFoundException(ErrorCode.MEMO_NOT_FOUND));
        if (memo.getWriterId().equals(writerId)) {
            throw new MemoNotMatchException(ErrorCode.MEMO_NOT_MATCH, "특정 메모를 수정하려했으나, 작성자가 일치하지 않습니다.");
        }
        validateMemoByWriterId(writerId, memo);
        memo.setTitle(memoUpdateDto.getNewTitle());
        memo.setContent(memoUpdateDto.getNewContent());
        memoRepository.save(memo);
    }

    public void deleteMemoByMemoId(UUID writerId, UUID memoId) {
        validateUser(writerId);
        Memo memo = memoRepository.findById(memoId).orElseThrow(() -> new MemoNotFoundException(ErrorCode.MEMO_NOT_FOUND));

        validateMemoByWriterId(writerId, memo);
        memoRepository.deleteById(memoId);
    }

    public void like(UUID memoId, UUID writerId) {
        validateUser(writerId);
        Memo memo = memoRepository.findById(memoId).orElseThrow(() -> new MemoNotFoundException(ErrorCode.MEMO_NOT_FOUND));
        User user = userRepository.findById(writerId).orElseThrow(() -> new UserNotFoundException(ErrorCode.USER_NOT_FOUND));

        memo.like(user);
        memoRepository.save(memo);
    }

    public void unlike(UUID memoId, UUID writerId) {
        validateUser(writerId);
        Memo memo = memoRepository.findById(memoId).orElseThrow(() -> new MemoNotFoundException(ErrorCode.MEMO_NOT_FOUND));
        User user = userRepository.findById(writerId).orElseThrow(() -> new UserNotFoundException(ErrorCode.USER_NOT_FOUND));

        memo.unlike(user);
        memoRepository.save(memo);
    }

    public LikeListResponseData getAllLikesInfo(UUID memoId) {
        Memo memo = memoRepository.findById(memoId)
                .orElseThrow(() -> new MemoNotFoundException(ErrorCode.MEMO_NOT_FOUND));
        List<MemoLike> likes = memo.getMemoLikes();

        return LikeListResponseData.builder()
                .likeList(likes)
                .likeCount(likes.size())
                .build();
    }

    private void validateUser(UUID writerId) {
        if (!userRepository.existsById(writerId)) {
            throw new UserNotFoundException(ErrorCode.USER_NOT_FOUND);
        }
    }
}
