package org.mjulikelion.week3assignment.repository;

import org.mjulikelion.week3assignment.model.Memo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface MemoRepository extends JpaRepository<Memo, UUID> {

    List<Memo> findByUserId(UUID id);

    List<Memo> findByUserIdIn(List<UUID> userIds);
}
