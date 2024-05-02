package org.mjulikelion.week3assignment.user.repository;

import org.mjulikelion.week3assignment.user.entity.User;

public interface UserRepository {
    void create(User user);

    boolean userExists(String userId);

    User findByUserId(String userId);

    void deleteUser(String userId);

    // 회원정보 수정 : userId
    void updateUserId(User user, String newName);
}
