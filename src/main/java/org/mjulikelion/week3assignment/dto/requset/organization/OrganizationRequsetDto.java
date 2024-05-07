package org.mjulikelion.week3assignment.dto.requset.organization;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class OrganizationRequsetDto {
    @NotNull(message = "userId가 null입니다.")
    private final UUID userId;
    @NotNull(message = "organizationId가 null입니다.")
    private final UUID organizationId;

    public OrganizationRequsetDto(UUID userId, UUID organizationId) {
        this.userId = userId;
        this.organizationId = organizationId;
    }
}
