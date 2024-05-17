package org.mjulikelion.week3assignment.dto.response.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.mjulikelion.week3assignment.model.User;

@Getter
@AllArgsConstructor
public class UserResponseData {
    private String email;
    private String name;

    public UserResponseData(User user) {
        this.email = user.getEmail();
        this.name = user.getName();
    }
}
