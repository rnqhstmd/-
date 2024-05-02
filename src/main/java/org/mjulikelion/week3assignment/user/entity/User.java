package org.mjulikelion.week3assignment.user.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
public class User {

    @Setter
    private String userId;
    private String name;
}
