package org.mjulikelion.week3assignment.sevice;

import lombok.AllArgsConstructor;
import org.mjulikelion.week3assignment.domain.User;
import org.mjulikelion.week3assignment.repository.repo_interface.MemoRepository;
import org.mjulikelion.week3assignment.repository.repo_interface.UserRepository;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public void createUser(User user) {
        userRepository.create(user);
    }

}
