package com.example.full.repository.member;

import com.example.full.entity.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email);

    Optional<Member> findByNickname(String email);

    boolean existsByEmail(String email);
    boolean existsByNickname(String nickname);
}
