package com.hello.hellospring.repository;

import com.hello.hellospring.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * 여긴 인터페이스만 있지만
 * spring-data-jpa는 JpaRepository 를 상속하는 것을 확인하면
 * 구현체를 자동으로 만들어 준다.
 */
public interface SpringDataJpaMemberRepository extends JpaRepository<Member, Long>, MemberRepository {

    /**
     * PK가 아닌 name으로 조회하는 경우는 공통 메서드화 하기 어렵다.
     * 그래서 여기서 별도로 선언해준다. 'findBy' 라는 지정된 키워드 뒤의 문법에 따라 jpql이 만들어진다.
     * "SELECT m FROM Member m WHERE m.name = ?"
     * @param name
     * @return
     */
    @Override
    Optional<Member> findByName(String name);

    /**
     * JpaRepository에 이미 여러 메서드
     * save, findById, ... 등이 존재하기 때문에 굳이 선언하지 않는다.
     */
}
