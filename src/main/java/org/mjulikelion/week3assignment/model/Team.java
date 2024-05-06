package org.mjulikelion.week3assignment.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "team") //team이라는 테이블
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "uuid2")// 자동생성되도록 함, uuid2라는 생성기를 통해서 자동 생성됨
    private UUID id;

    @Column(nullable = false, length = 100)//컬
    private String name;

    @OneToMany(mappedBy = "team", fetch = FetchType.EAGER)
    //테이블 이름을 넣어줘야 함 - 변수명만 맞으면 됨 member에서 FK로 갖고있는 것의 변수명
    private List<Member> members;

}
