package org.mjulikelion.week3assignment.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.mjulikelion.week3assignment.dto.requset.organization.OrganizationCreateDto;
import org.mjulikelion.week3assignment.dto.requset.organization.OrganizationRequsetDto;
import org.mjulikelion.week3assignment.dto.response.ResponseDto;
import org.mjulikelion.week3assignment.dto.response.memo.MemoListResponseData;
import org.mjulikelion.week3assignment.service.MemoService;
import org.mjulikelion.week3assignment.service.OrganizationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/organizations")
public class OrganizationController {

    private final OrganizationService organizationService;
    private final MemoService memoService;

    @PostMapping
    public ResponseEntity<ResponseDto<Void>> createOrganization(@Valid @RequestBody OrganizationCreateDto organizationCreateDto) {
        organizationService.createOrganization(organizationCreateDto);
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.CREATED, "소속 생성 완료"), HttpStatus.CREATED);
    }

    @PostMapping("{id}/join")
    public ResponseEntity<ResponseDto<Void>> joinOrganization(@PathVariable("id") UUID organizationId, @RequestHeader("User-Id") UUID userId) {
        OrganizationRequsetDto joinOrganizationDto = OrganizationRequsetDto.builder()
                .userId(userId)
                .organizationId(organizationId)
                .build();
        organizationService.joinOrganization(joinOrganizationDto);
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.ACCEPTED, "소속 가입 완료"), HttpStatus.ACCEPTED);
    }

    @GetMapping("/{id}/memos")
    public ResponseEntity<ResponseDto<MemoListResponseData>> getAllOrganizationMemos(@PathVariable("id") UUID organizationId, @RequestHeader("User-Id") UUID userId) {
        OrganizationRequsetDto organizationRequsetDto = OrganizationRequsetDto.builder()
                .userId(userId)
                .organizationId(organizationId)
                .build();
        MemoListResponseData allOrganizationMemos = memoService.getAllOrganizationMemos(organizationRequsetDto);
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.OK, "소속 메모 조회 완료", allOrganizationMemos), HttpStatus.OK);
    }

    @DeleteMapping("/{id}/exit")
    public ResponseEntity<ResponseDto<Void>> exitOrganization(@PathVariable("id") UUID organizationId, @RequestHeader("User-Id") UUID userId) {
        organizationService.exitOrganization(organizationId, userId);
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.OK, "소속 탈퇴 완료"), HttpStatus.OK);
    }
}
