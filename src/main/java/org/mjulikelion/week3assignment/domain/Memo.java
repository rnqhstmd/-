package org.mjulikelion.week3assignment.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Getter
public class Memo {

    private String memoId;

    @Setter
    private String content;

    private String userId;
}
