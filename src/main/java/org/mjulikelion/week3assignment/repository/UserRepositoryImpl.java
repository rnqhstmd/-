package org.mjulikelion.week3assignment.repository;

import org.mjulikelion.week3assignment.domain.User;
import org.mjulikelion.week3assignment.repository.repo_interface.UserRepository;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private Map<String, User> users = new HashMap<>();

    @Override
    public void createUser(User user) {
        users.put(user.getUserId(), user);
    }
}
