package org.mjulikelion.week3assignment.dto.response.memo;

import lombok.Getter;
import org.mjulikelion.week3assignment.dto.response.user.UserResponse;
import org.mjulikelion.week3assignment.model.User;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class LikeListResponseData {
    private final List<UserResponse> likeUserList;
    private final int likeCount;

    public LikeListResponseData(List<User> users) {
        this.likeUserList = users.stream()
                .map(user -> new UserResponse(user))
                .collect(Collectors.toList());
        this.likeCount = likeUserList.size();
    }
}
