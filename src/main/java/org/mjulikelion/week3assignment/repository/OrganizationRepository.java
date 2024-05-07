package org.mjulikelion.week3assignment.repository;

import org.mjulikelion.week3assignment.model.Organization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OrganizationRepository extends JpaRepository<Organization, UUID> {
    boolean existsByName(String name);
}
