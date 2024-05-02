package org.mjulikelion.week3assignment.user.service;

import lombok.AllArgsConstructor;
import org.mjulikelion.week3assignment.user.dto.UserCreateDto;
import org.mjulikelion.week3assignment.user.dto.UserUpdateDto;
import org.mjulikelion.week3assignment.user.entity.User;
import org.mjulikelion.week3assignment.user.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public void createUser(UserCreateDto userCreateDto) {
        User user = new User(userCreateDto.getUserId(), userCreateDto.getName());
        userRepository.create(user);
    }

    public void updateUser(String userid, String newUserId, UserUpdateDto userUpdateDto) {
        User user = userRepository.findByUserId(userid);
        userRepository.updateUserId(user, newUserId);
    }

    public void deleteUser(String userId) {
        userRepository.deleteUser(userId);
    }

}
