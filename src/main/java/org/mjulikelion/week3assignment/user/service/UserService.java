package org.mjulikelion.week3assignment.user.service;

import lombok.AllArgsConstructor;
import org.mjulikelion.week3assignment.exception.UserAlreadyExists;
import org.mjulikelion.week3assignment.exception.UserNotFoundException;
import org.mjulikelion.week3assignment.exception.UserNotMatchException;
import org.mjulikelion.week3assignment.exception.errorcode.ErrorCode;
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
        if (userRepository.userExists(user.getUserId())) {
            throw new UserAlreadyExists(ErrorCode.USER_ALREADY_EXISTS);
        }
        userRepository.create(user);
    }

    public void updateUser(String userId, UserUpdateDto userUpdateDto) {

        User user = userRepository.findByUserId(userId);

        if (!userRepository.userExists(user.getUserId())) {
            throw new UserNotFoundException(ErrorCode.USER_NOT_FOUND);
        } else if (!user.getUserId().equals(userUpdateDto.getNewUserId())) {
            throw new UserNotMatchException(ErrorCode.USER_NOT_MATCH, "회원 정보를 수정하려했으나, userId가 같지 않습니다.");
        } else if (userRepository.userExists(userUpdateDto.getNewUserId())) {
            throw new UserAlreadyExists(ErrorCode.USER_ALREADY_EXISTS);
        }

        userRepository.updateUser(user, userUpdateDto.getNewUserId(), userUpdateDto.getNewName());
    }

    public void deleteUser(String userId) {

        User user = userRepository.findByUserId(userId);

        if (!userRepository.userExists(userId)) {
            throw new UserNotFoundException(ErrorCode.USER_NOT_FOUND);
        } else if (!user.getUserId().equals(userId)) {
            throw new UserNotMatchException(ErrorCode.USER_NOT_MATCH, "회원 정보를 삭제하려했으나, userId가 같지 않습니다.");
        }
        userRepository.deleteUser(userId);
    }
}
