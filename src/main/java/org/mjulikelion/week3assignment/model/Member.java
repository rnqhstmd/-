package org.mjulikelion.week3assignment.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "member") //team이라는 테이블
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "uuid2")// 자동생성되도록 함, uuid2라는 생성기를 통해서 자동 생성됨
    private UUID id;

    @ManyToOne(fetch = FetchType.EAGER)
    private Team team;

}