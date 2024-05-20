package org.mjulikelion.week3assignment.dto.response.user;

import lombok.Getter;
import org.mjulikelion.week3assignment.model.User;

@Getter
public class UserResponse {
    private final String id;
    private final String email;

    public UserResponse(User user) {
        this.id = String.valueOf(user.getId());
        this.email = user.getEmail();
    }
}
