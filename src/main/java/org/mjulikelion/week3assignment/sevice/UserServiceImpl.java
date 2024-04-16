package org.mjulikelion.week3assignment.sevice;

import lombok.AllArgsConstructor;
import org.mjulikelion.week3assignment.domain.User;
import org.mjulikelion.week3assignment.repository.repo_interface.UserRepository;
import org.mjulikelion.week3assignment.sevice.service_interface.UserService;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    @Override
    public void createUser(User user) {
        userRepository.createUser(user);
    }
}
