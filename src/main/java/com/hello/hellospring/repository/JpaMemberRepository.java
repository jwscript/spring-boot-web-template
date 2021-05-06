package com.hello.hellospring.repository;

import com.hello.hellospring.domain.Member;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class JpaMemberRepository implements MemberRepository {

    /**
     * ★ JPA는 EntityManager에 의해 모든 동작이 처리된다.
     * build.gradle에서 spring-boot-starter-data-jpa 를 받으면 스프링 부트가 자동으로 EntityManager 타입의 스프링 빈을 만들어 줌.
     * 우리는 생성자에서 주입하면 됨.
     */
    private final EntityManager em;

    /**
     * ※ 생성자가 하나이므로, @Autowired가 없어도 있는 것처럼 동작한다.
     *
     * @param em
     */
    public JpaMemberRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public Member save(Member member) {
        em.persist(member);
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        Member member = em.find(Member.class, id);
        return Optional.ofNullable(member);
    }

    @Override
    public Optional<Member> findByName(String name) {
        List<Member> result = em.createQuery("SELECT m FROM Member m WHERE m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();

        return result.stream().findAny();
    }

    @Override
    public List<Member> findAll() {
        /**
         * 1. createQuery()
         * 첫번째 파라미터는 Entity인 'Member'를 대상으로 날리는 쿼리.
         * 이때 'm' 을 SELECT하는데, m은 객체를 의미한다.
         * 즉, 객체 자체를 조회하는 것임.
         *
         * 두번째 파라미터는 반환 타입
         */
        return em.createQuery("SELECT m FROM Member m", Member.class)
                .getResultList();
    }
}

/**
 * PK가 아닌 파라미터로 find 하게 될 때, jpql을 작성해줘야 한다.
 * 하지만, Spring-data-jpa를 쓰게 되면 jpql을 작성하는 것 마저 하지 않을 수 있다.
 */