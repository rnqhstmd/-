package org.mjulikelion.week3assignment.user.repository;

import org.mjulikelion.week3assignment.user.entity.User;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private final Map<String, User> users = new HashMap<>();

    @Override
    public void create(User user) {
        users.put(user.getUserId(), user);
    }

    @Override
    public boolean userExists(String userId) {
        return users.containsKey(userId);
    }

    @Override
    public User findByUserId(String userId) {
        return users.get(userId);
    }

    @Override
    public void deleteUser(String userId) {
        users.remove(userId);
    }

    @Override
    public void updateUser(User user, String newUserId, String newName) {

        // 사용자의 oldUserId, oldName 삭제
        User oldUser = users.get(user.getUserId());
        users.remove(oldUser);

        // newUserId, newName 업데이트
        user.setUserId(newUserId);
        user.setName(newName);
        users.put(user.getUserId(), user);
    }
}
