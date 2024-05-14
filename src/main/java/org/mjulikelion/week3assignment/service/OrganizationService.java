package org.mjulikelion.week3assignment.service;

import lombok.AllArgsConstructor;
import org.mjulikelion.week3assignment.dto.requset.organization.OrganizationCreateDto;
import org.mjulikelion.week3assignment.dto.requset.organization.OrganizationRequsetDto;
import org.mjulikelion.week3assignment.exception.ConflictException;
import org.mjulikelion.week3assignment.exception.NotFoundException;
import org.mjulikelion.week3assignment.exception.errorcode.ErrorCode;
import org.mjulikelion.week3assignment.model.Organization;
import org.mjulikelion.week3assignment.model.User;
import org.mjulikelion.week3assignment.model.UserOrganization;
import org.mjulikelion.week3assignment.repository.OrganizationRepository;
import org.mjulikelion.week3assignment.repository.UserOrganizationRepository;
import org.mjulikelion.week3assignment.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class OrganizationService {

    private OrganizationRepository organizationRepository;
    private UserRepository userRepository;
    private UserOrganizationRepository userOrganizationRepository;

    // 소속 생성
    public void createOrganization(OrganizationCreateDto organizationCreateDto) {

        validateOrganizationByName(organizationCreateDto);

        Organization organization = Organization.builder()
                .name(organizationCreateDto.getName())
                .introduction(organizationCreateDto.getIntroduction())
                .build();
        organizationRepository.save(organization);
    }

    // 소속 가입
    public void joinOrganization(OrganizationRequsetDto joinOrganizationDto) {
        User user = validUser(joinOrganizationDto);
        Organization organization = validOrganization(joinOrganizationDto);

        UserOrganization userOrganization = UserOrganization.builder()
                .user(user)
                .organization(organization)
                .build();

        userOrganizationRepository.save(userOrganization);
    }

    // 소속 탈퇴
    public void exitOrganization(UUID userId, UUID organizationId) {
        UserOrganization userOrganization = validateUserOrganization(userId, organizationId);

        userOrganizationRepository.delete(userOrganization);
    }

    // 소속 이름이 이미 존재하는지 검증
    private void validateOrganizationByName(OrganizationCreateDto organizationCreateDto) {
        if (organizationRepository.existsByName(organizationCreateDto.getName())) {
            throw new ConflictException(ErrorCode.ORGANIZATION_ALREADY_EXISTS);
        }
    }

    // 유저id로 존재 검증
    private User validUser(OrganizationRequsetDto joinOrganizationDto) {
        return userRepository.findById(joinOrganizationDto.getUserId())
                .orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND));
    }

    // 소속id로 존재 검증
    private Organization validOrganization(OrganizationRequsetDto joinOrganizationDto) {
        return organizationRepository.findById(joinOrganizationDto.getOrganizationId())
                .orElseThrow(() -> new NotFoundException(ErrorCode.ORGANIZATION_NOT_FOUND));
    }

    // 유저id, 소속id로 소속 반환
    private UserOrganization validateUserOrganization(UUID userId, UUID organizationId) {
        return userOrganizationRepository.findByUserIdAndOrganizationId(userId, organizationId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.ORGANIZATION_NOT_FOUND));
    }
}
