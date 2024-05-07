package org.mjulikelion.week3assignment.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "memo_like")
public class MemoLike extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @JsonIgnore
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Memo memo;
    @JsonIgnore
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private User user;

    public MemoLike(Memo memo, User user) {
        this.memo = memo;
        this.user = user;
    }

    public boolean isLikedByUser(User user) {
        return this.user.equals(user);
    }
}
