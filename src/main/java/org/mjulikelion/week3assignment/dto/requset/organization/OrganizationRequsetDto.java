package org.mjulikelion.week3assignment.dto.requset.organization;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
public class OrganizationRequsetDto {
    @NotNull(message = "userId가 비어있습니다.")
    private final UUID userId;
    @NotNull(message = "organizationId가 비어있습니다.")
    private final UUID organizationId;
}
