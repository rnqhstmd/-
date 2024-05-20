package org.mjulikelion.week3assignment.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mjulikelion.week3assignment.dto.requset.organization.OrganizationCreateDto;
import org.mjulikelion.week3assignment.exception.ConflictException;
import org.mjulikelion.week3assignment.exception.NotFoundException;
import org.mjulikelion.week3assignment.exception.errorcode.ErrorCode;
import org.mjulikelion.week3assignment.model.Organization;
import org.mjulikelion.week3assignment.model.User;
import org.mjulikelion.week3assignment.model.UserOrganization;
import org.mjulikelion.week3assignment.repository.OrganizationRepository;
import org.mjulikelion.week3assignment.repository.UserOrganizationRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@AllArgsConstructor
public class OrganizationService {

    private OrganizationRepository organizationRepository;
    private UserOrganizationRepository userOrganizationRepository;

    // 소속 생성
    public void createOrganization(OrganizationCreateDto organizationCreateDto) {

        // 소속 이름이 이미 존재하는지 검증
        validateOrganizationByName(organizationCreateDto.getName());

        Organization organization = Organization.builder()
                .name(organizationCreateDto.getName())
                .introduction(organizationCreateDto.getIntroduction())
                .build();
        organizationRepository.save(organization);
    }

    public void joinOrganization(User user, UUID organizationId) {

        // 소속 존재 검증
        Organization organization = validOrganization(organizationId);

        // 소속에 이미 존재하는 유저인지 검증
        validateUserNotInOrganization(user.getId(), organizationId);

        UserOrganization userOrganization = UserOrganization.builder()
                .user(user)
                .organization(organization)
                .build();

        userOrganizationRepository.save(userOrganization);
    }

    // 소속 탈퇴
    public void exitOrganization(UUID userId, UUID organizationId) {
        log.info("userId={}, organizationId={}", userId, organizationId);
        // 유저id, 소속id로 소속 검증
        UserOrganization userOrganization = validateUserOrganization(userId, organizationId);
        userOrganizationRepository.delete(userOrganization);
    }

    public void validateUserNotInOrganization(UUID userId, UUID organizationId) {
        if (userOrganizationRepository.existsByUserIdAndOrganizationId(userId, organizationId)) {
            throw new ConflictException(ErrorCode.USER_ALREADY_EXISTS);
        }
    }

    private void validateOrganizationByName(String name) {
        if (organizationRepository.existsByName(name)) {
            throw new ConflictException(ErrorCode.ORGANIZATION_ALREADY_EXISTS);
        }
    }

    private Organization validOrganization(UUID organizationId) {
        return organizationRepository.findById(organizationId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.ORGANIZATION_NOT_FOUND));
    }

    private UserOrganization validateUserOrganization(UUID userId, UUID organizationId) {
        return userOrganizationRepository.findByUserIdAndOrganizationId(userId, organizationId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.ORGANIZATION_NOT_FOUND));
    }
}
