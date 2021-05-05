package com.hello.hellospring.repository;

import com.hello.hellospring.domain.Member;

import java.util.List;
import java.util.Optional;

/**
 * 저장소 rep 인터페이스
 */
public interface MemberRepository {
    // 회원 정보 저장
    Member save(Member member);

    // id로 회원 정보 찾기
    Optional<Member> findById(Long id);

    // name으로 회원 정보 찾기
    Optional<Member> findByName(String name);

    // 전체 회원 정보 조회
    List<Member> findAll();
}
