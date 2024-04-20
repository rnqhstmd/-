package org.mjulikelion.week3assignment.repository.repo_interface;

import org.mjulikelion.week3assignment.domain.User;

public interface UserRepository extends Repository<User> {
    void create(User user);
}
