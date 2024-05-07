package org.mjulikelion.week3assignment.service;

import lombok.AllArgsConstructor;
import org.mjulikelion.week3assignment.dto.requset.organization.OrganizationCreateDto;
import org.mjulikelion.week3assignment.dto.requset.organization.OrganizationRequsetDto;
import org.mjulikelion.week3assignment.exception.OrganizationAlreadyExistsException;
import org.mjulikelion.week3assignment.exception.OrganizationNotFoundException;
import org.mjulikelion.week3assignment.exception.UserNotFoundException;
import org.mjulikelion.week3assignment.exception.errorcode.ErrorCode;
import org.mjulikelion.week3assignment.model.Organization;
import org.mjulikelion.week3assignment.model.User;
import org.mjulikelion.week3assignment.model.UserOrganization;
import org.mjulikelion.week3assignment.repository.OrganizationRepository;
import org.mjulikelion.week3assignment.repository.UserOrganizationRepository;
import org.mjulikelion.week3assignment.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@AllArgsConstructor
public class OrganizationService {

    private OrganizationRepository organizationRepository;
    private UserRepository userRepository;
    private UserOrganizationRepository userOrganizationRepository;

    // 소속 생성
    public void createOrganization(OrganizationCreateDto organizationCreateDto) {
        if (organizationRepository.existsByName(organizationCreateDto.getName())) {
            throw new OrganizationAlreadyExistsException(ErrorCode.ORGANIZATION_ALREADY_EXISTS);
        }

        Organization organization = Organization.builder()
                .name(organizationCreateDto.getName())
                .introduction(organizationCreateDto.getIntroduction())
                .build();
        organizationRepository.save(organization);
    }

    // 소속 가입
    public void joinOrganization(OrganizationRequsetDto joinOrganizationDto) {
        User user = userRepository.findById(joinOrganizationDto.getUserId())
                .orElseThrow(() -> new UserNotFoundException(ErrorCode.USER_NOT_FOUND));
        Organization organization = organizationRepository.findById(joinOrganizationDto.getOrganizationId())
                .orElseThrow(() -> new OrganizationNotFoundException(ErrorCode.ORGANIZATION_NOT_FOUND));

        UserOrganization userOrganization = UserOrganization.builder()
                .user(user)
                .organization(organization)
                .joinAt(LocalDateTime.now())
                .build();

        userOrganizationRepository.save(userOrganization);
    }

    // 소속 탈퇴
    public void exitOrganization(UUID userId, UUID organizationId) {
        UserOrganization userOrganization = userOrganizationRepository.findByUserIdAndOrganizationId(userId, organizationId)
                .orElseThrow(() -> new OrganizationNotFoundException(ErrorCode.ORGANIZATION_NOT_FOUND));

        userOrganizationRepository.delete(userOrganization);
    }
}
