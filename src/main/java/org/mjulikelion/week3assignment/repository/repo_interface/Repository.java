package org.mjulikelion.week3assignment.repository.repo_interface;

import java.util.List;

public interface Repository<T> {
    void create(T instance);  // 회원 or 메모 저장
}
