package org.mjulikelion.week3assignment.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity(name = "organization")
public class Organization extends BaseEntity {

    @Column(nullable = false, length = 100, unique = true, name = "organization_name", updatable = false)
    private String name;

    @Column(nullable = false, length = 100, unique = true, name = "organization_introduction", updatable = false)
    private String introduction;

    @OneToMany(mappedBy = "organization", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserOrganization> userOrganizations;
}
