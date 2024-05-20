package org.mjulikelion.week3assignment.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mjulikelion.week3assignment.dto.requset.memo.MemoCreateDto;
import org.mjulikelion.week3assignment.dto.requset.memo.MemoUpdateDto;
import org.mjulikelion.week3assignment.dto.response.memo.LikeListResponseData;
import org.mjulikelion.week3assignment.dto.response.memo.MemoListResponseData;
import org.mjulikelion.week3assignment.dto.response.memo.MemoResponseData;
import org.mjulikelion.week3assignment.exception.ForbiddenException;
import org.mjulikelion.week3assignment.exception.NotFoundException;
import org.mjulikelion.week3assignment.exception.errorcode.ErrorCode;
import org.mjulikelion.week3assignment.model.Memo;
import org.mjulikelion.week3assignment.model.MemoLike;
import org.mjulikelion.week3assignment.model.User;
import org.mjulikelion.week3assignment.model.UserOrganization;
import org.mjulikelion.week3assignment.repository.MemoRepository;
import org.mjulikelion.week3assignment.repository.OrganizationRepository;
import org.mjulikelion.week3assignment.repository.UserOrganizationRepository;
import org.mjulikelion.week3assignment.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class MemoService {

    private final UserRepository userRepository;
    private final MemoRepository memoRepository;
    private final OrganizationRepository organizationRepository;
    private final UserOrganizationRepository userOrganizationRepository;

    // 메모 Id 로 메모 조회
    public MemoResponseData getMemoByMemoId(UUID memoId) {

        // 메모id에 해당하는 메모가 존재하는지 검증
        Memo memo = validateMemoByMemoId(memoId);

        return new MemoResponseData(memo);
    }

    // 유저 Id 로 모든 메모 조회
    public MemoListResponseData getAllMemosByWriterId(UUID writerId) {

        List<Memo> memoList = memoRepository.findByUserId(writerId);

        return new MemoListResponseData(memoList);
    }

    // 특정 소속 내 유저들 모든 메모 조회
    public MemoListResponseData getAllOrganizationMemos(User user, UUID organizationId) {

        // 소속 존재 검증
        validateUserOrganizationRepository(organizationId);

        // 소속에 가입된 유저 id 추출
        List<UserOrganization> userOrganization = userOrganizationRepository.findByOrganizationId(organizationId);
        log.info("organizationRequsetDto.getOrganizationId()={}", organizationId);

        List<UUID> userIds = userOrganization.stream()
                .map(UserOrganization::getUser)
                .map(User::getId)
                .collect(Collectors.toList());
        log.info("userIds={}", userIds);

        // 해당 유저들의 모든 메모 조회
        List<Memo> memoList = memoRepository.findByUserIdIn(userIds);
        log.info("memoList={}", memoList);

        return new MemoListResponseData(memoList);
    }

    // 메모 생성
    public void createMemo(User user, MemoCreateDto memoCreateDto) {

        Memo memo = Memo.builder()
                .title(memoCreateDto.getTitle())
                .content(memoCreateDto.getContent())
                .user(user)
                .build();

        memoRepository.save(memo);
    }

    // 메모 Title, Content 수정
    public void updateMemoByMemoId(UUID userId, UUID memoId, MemoUpdateDto memoUpdateDto) {

        // 메모id에 해당하는 메모가 존재하는지 검증
        Memo memo = validateMemoByMemoId(memoId);
        // 해당 메모를 작성한 유저와 일치하는지 검증
        validateMemoByWriterId(userId, memo);

        memo.setTitle(memoUpdateDto.getNewTitle());
        memo.setContent(memoUpdateDto.getNewContent());

        memoRepository.save(memo);
    }

    public void deleteMemoByMemoId(UUID writerId, UUID memoId) {

        // 메모id에 해당하는 메모가 존재하는지 검증
        Memo memo = validateMemoByMemoId(memoId);
        // 해당 메모를 작성한 유저와 일치하는지 검증
        validateMemoByWriterId(writerId, memo);

        memoRepository.deleteById(memoId);
    }

    public void like(User user, UUID memoId) {

        // 메모id에 해당하는 메모가 존재하는지 검증
        Memo memo = validateMemoByMemoId(memoId);

        memo.like(user);
        memoRepository.save(memo);
    }

    public void unlike(User user, UUID memoId) {

        // 메모id에 해당하는 메모가 존재하는지 검증
        Memo memo = validateMemoByMemoId(memoId);

        memo.unlike(user);
        memoRepository.save(memo);
    }

    public LikeListResponseData getAllLikesInfo(UUID memoId) {

        // 메모id에 해당하는 메모가 존재하는지 검증
        Memo memo = validateMemoByMemoId(memoId);
        List<MemoLike> likes = memo.getMemoLikes();

        // 좋아요 누른 사용자 목록 추출
        List<User> users = likes.stream()
                .map(MemoLike::getUser)
                .collect(Collectors.toList());

        return new LikeListResponseData(users);
    }

    private void validateUserOrganizationRepository(UUID organizationId) {
        if (organizationRepository.findById(organizationId).isEmpty()) {
            throw new NotFoundException(ErrorCode.ORGANIZATION_NOT_FOUND);
        }
    }

    private Memo validateMemoByMemoId(UUID memoId) {
        return memoRepository.findById(memoId).orElseThrow(() -> new NotFoundException(ErrorCode.MEMO_NOT_FOUND));
    }

    private void validateMemoByWriterId(UUID userId, Memo memo) {
        if (!memo.getUser().getId().equals(userId)) {
            throw new ForbiddenException(ErrorCode.USER_NOT_MATCH);
        }
    }
}
