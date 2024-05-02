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
        if (userExists(user.getUserId())) {
            throw new IllegalArgumentException("이미 존재하는 아이디입니다.");
        }
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
        if (!userExists(userId)) {
            throw new IllegalArgumentException("존재하지 않는 아이디입니다.");
        }
        users.remove(userId);
    }

    @Override
    public void updateUserId(User user, String newUserId) {

        if (!userExists(user.getUserId())) {
            throw new IllegalArgumentException("존재하지 않는 아이디입니다.");
        } else if (!user.getUserId().equals(newUserId)) {
            throw new IllegalArgumentException("이미 존재하는 아이디입니다.");
        } else if (userExists(newUserId)) {
            throw new IllegalArgumentException("이미 존재하는 아이디입니다.");
        }

        // 사용자의 oldId를 삭제
        User oldUser = users.get(user.getUserId());
        users.remove(oldUser);

        // newId로 업데이트
        user.setUserId(newUserId);
        users.put(user.getUserId(), user);
    }
}
