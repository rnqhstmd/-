package org.mjulikelion.week3assignment.domain.dto;

import lombok.Getter;

@Getter
public class MemoGetDto {

    private final String content;

    public MemoGetDto(String content) {
        this.content = content;
    }
}
