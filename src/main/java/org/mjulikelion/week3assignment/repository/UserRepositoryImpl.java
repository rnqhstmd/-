package org.mjulikelion.week3assignment.repository;

import org.mjulikelion.week3assignment.domain.User;
import org.mjulikelion.week3assignment.repository.repo_interface.MemoRepository;
import org.mjulikelion.week3assignment.repository.repo_interface.UserRepository;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
@Repository
public class UserRepositoryImpl implements UserRepository {

    private List<User> users = new LinkedList<>();

    @Override
    public void create(User user) {
        if (userExists(user.getUserId())) {
            throw new IllegalArgumentException("이미 존재하는 아이디입니다.");
        }
        users.add(user);
    }

    @Override
    public boolean userExists(String userId) {
        return users.contains(userId);
    }
}
