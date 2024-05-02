package org.mjulikelion.week3assignment.memo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
public class Memo {

    private String memoId;
    @JsonIgnore // 유저아이디를 보호하기 위해 사용된다.
    private String userId;
    @Setter
    private String title;
    @Setter
    private String content;
}
