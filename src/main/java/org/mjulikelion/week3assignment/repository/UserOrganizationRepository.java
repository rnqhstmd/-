package org.mjulikelion.week3assignment.repository;

import org.mjulikelion.week3assignment.model.UserOrganization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserOrganizationRepository extends JpaRepository<UserOrganization, UUID> {

    Optional<UserOrganization> findByUserIdAndOrganizationId(UUID userId, UUID organizationId);

    List<UserOrganization> findByOrganizationId(UUID organizationId);
}
