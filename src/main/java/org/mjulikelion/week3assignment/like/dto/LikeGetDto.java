package org.mjulikelion.week3assignment.like.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
public class LikeGetDto {

    private List<LocalDateTime> likeTime;
    private int totalLikes;
    private List<String> userNames;
}
