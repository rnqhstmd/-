package org.mjulikelion.week3assignment.like.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LikeRequestDto {

    private final String userId;
    private final String memoId;
}
