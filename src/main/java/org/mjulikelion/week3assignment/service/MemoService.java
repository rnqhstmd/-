package org.mjulikelion.week3assignment.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mjulikelion.week3assignment.dto.requset.memo.MemoCreateDto;
import org.mjulikelion.week3assignment.dto.requset.memo.MemoUpdateDto;
import org.mjulikelion.week3assignment.dto.requset.organization.OrganizationRequsetDto;
import org.mjulikelion.week3assignment.dto.response.memo.LikeListResponseData;
import org.mjulikelion.week3assignment.dto.response.memo.MemoListResponseData;
import org.mjulikelion.week3assignment.dto.response.memo.MemoResponse;
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

    public MemoResponseData getMemoByMemoId(UUID memoId) {

        MemoResponse memoResponseDto = MemoResponse.builder()
                .id(String.valueOf(memoRepository.findById(memoId)))
                .build();

        // 메모id에 해당하는 메모가 존재하는지 검증
        Memo memo = validateMemo(memoId);

        int likeCount = memo.getMemoLikes().size();

        return new MemoResponseData(memoResponseDto, likeCount);
    }


    public MemoListResponseData getAllMemosByWriterId(UUID writerId) {
        validateUserExists(writerId);

        List<Memo> memoList = memoRepository.findByUserId(writerId);

        // Memo 객체를 MemoResponse 로 매핑 후 리스트로 반환
        List<MemoResponse> memoResponses = memoList.stream()
                .map(memo -> MemoResponse.builder()
                        .id(String.valueOf(memo.getId()))
                        .title(memo.getTitle())
                        .content(memo.getContent())
                        .build())
                .collect(Collectors.toList());

        // MemoListResponseData엔 MemoResponse를 사용
        return MemoListResponseData.builder()
                .memoList(memoResponses)
                .total(memoResponses.size())
                .build();
    }


    // 특정 소속 내 유저들 모든 메모 조회
    public MemoListResponseData getAllOrganizationMemos(OrganizationRequsetDto organizationRequsetDto) {

        validateUserOrganizationRepository(organizationRequsetDto.getOrganizationId());

        // 소속에 가입된 유저 id 추출
        List<UserOrganization> userOrganization = userOrganizationRepository.findByOrganizationId(organizationRequsetDto.getOrganizationId());
        log.info("organizationRequsetDto.getOrganizationId()={}", organizationRequsetDto.getOrganizationId());

        List<UUID> userIds = userOrganization.stream()
                .map(userOrga -> userOrga.getUser())
                .map(user -> user.getId())
                .collect(Collectors.toList());
        log.info("userIds={}", userIds);

        List<Memo> memoList = memoRepository.findByUserIdIn(userIds);
        log.info("memoList={}", memoList);

        List<MemoResponse> memoResponses = memoList.stream()
                .map(memo -> MemoResponse.builder()
                        .title(memo.getTitle())
                        .id(String.valueOf(memo.getId()))
                        .content(memo.getContent())
                        .build())
                .collect(Collectors.toList());

        return MemoListResponseData.builder()
                .memoList(memoResponses)
                .total(memoResponses.size())
                .build();
    }

    public void createMemo(UUID writerId, MemoCreateDto memoCreateDto) {
        User user = validateUser(writerId);

        Memo memo = Memo.builder()
                .title(memoCreateDto.getTitle())
                .content(memoCreateDto.getContent())
                .user(user)
                .build();

        memoRepository.save(memo);
    }

    public void updateMemoByMemoId(UUID userId, UUID memoId, MemoUpdateDto memoUpdateDto) {

        validateUserExists(userId);
        Memo memo = validateMemo(memoId);
        validateMemoByWriterId(userId, memo);

        memo.setTitle(memoUpdateDto.getNewTitle());
        memo.setContent(memoUpdateDto.getNewContent());
        memoRepository.save(memo);
    }

    public void deleteMemoByMemoId(UUID writerId, UUID memoId) {
        validateUserExists(writerId);
        Memo memo = validateMemo(memoId);
        validateMemoByWriterId(writerId, memo);

        memoRepository.deleteById(memoId);
    }

    public void like(UUID memoId, UUID writerId) {
        validateUserExists(writerId);
        Memo memo = validateMemo(memoId);
        User user = validateUser(writerId);

        memo.like(user);
        memoRepository.save(memo);
    }

    public void unlike(UUID memoId, UUID writerId) {

        validateUserExists(writerId);
        User user = validateUser(writerId);
        Memo memo = validateMemo(memoId);

        memo.unlike(user);
        memoRepository.save(memo);
    }

    public LikeListResponseData getAllLikesInfo(UUID memoId) {
        Memo memo = validateMemo(memoId);
        List<MemoLike> likes = memo.getMemoLikes();

        List<MemoResponse> memoResponses = likes.stream()
                .map(like -> MemoResponse.builder()
                        .id(String.valueOf(like.getMemo().getId()))
                        .title(like.getMemo().getTitle())
                        .content(like.getMemo().getContent())
                        .build())
                .collect(Collectors.toList());

        return LikeListResponseData.builder()
                .likeList(memoResponses)
                .likeCount(likes.size())
                .build();
    }

    // 소속 존재 검증
    private void validateUserOrganizationRepository(UUID organizationId) {
        if (organizationRepository.findById(organizationId).isEmpty()) {
            throw new NotFoundException(ErrorCode.ORGANIZATION_NOT_FOUND);
        }
    }

    // 메모id에 해당하는 메모가 존재하는지 검증
    private Memo validateMemo(UUID memoId) {
        return memoRepository.findById(memoId).orElseThrow(() -> new NotFoundException(ErrorCode.MEMO_NOT_FOUND));
    }

    // 유저id에 해당하는 유저가 존재하는지 검증하고, 찾은 유저를 반환
    private User validateUser(UUID userId) {
        return userRepository.findById(userId).orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND));
    }

    // 유저 id로 존재 검증
    private void validateUserExists(UUID writerId) {
        if (!userRepository.existsById(writerId)) {
            throw new NotFoundException(ErrorCode.USER_NOT_FOUND);
        }
    }

    // 해당 메모를 작성한 유저와 일치하는지 검증
    private void validateMemoByWriterId(UUID userId, Memo memo) {
        if (!memo.getUser().getId().equals(userId)) {
            throw new ForbiddenException(ErrorCode.USER_NOT_MATCH);
        }
    }
}
