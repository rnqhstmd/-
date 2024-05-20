package org.mjulikelion.week3assignment.model;

import jakarta.persistence.*;
import lombok.*;
import org.mjulikelion.week3assignment.exception.ConflictException;
import org.mjulikelion.week3assignment.exception.NotFoundException;
import org.mjulikelion.week3assignment.exception.errorcode.ErrorCode;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "memo")
public class Memo extends BaseEntity {

    @Setter
    @Column(length = 100, nullable = false)// 길이는 100자 이하이고, 비어있을 수 없다.
    private String title;

    @Setter
    @Column(length = 2000, nullable = false)// 길이는 2000자 이하이고, 비어있을 수 없다.
    private String content;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private User user;

    @OneToMany(mappedBy = "memo", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<MemoLike> memoLikes;

    public void like(User user) {
        if (isLikedByUser(user)) {
            throw new ConflictException(ErrorCode.LIKE_ALREADY_EXISTS);
        }
        this.memoLikes.add(new MemoLike(this, user));
    }

    public void unlike(User user) {
        MemoLike like = this.memoLikes.stream()
                .filter(l -> l.getUser().equals(user))
                .findFirst()
                .orElseThrow(() -> new NotFoundException(ErrorCode.LIKE_NOT_FOUND));
        this.memoLikes.remove(like);
    }

    private boolean isLikedByUser(User user) {
        return this.memoLikes.stream()
                .anyMatch(like -> like.getUser().equals(user));
    }
}
