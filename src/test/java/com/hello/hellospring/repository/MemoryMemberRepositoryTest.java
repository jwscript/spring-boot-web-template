package com.hello.hellospring.repository;

import com.hello.hellospring.domain.Member;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class MemoryMemberRepositoryTest {
    MemoryMemberRepository repository = new MemoryMemberRepository();

    /**
     * @AfterEach 어노테이션
     * 테스트가 하나 끝날 때마다 아래 메서드가 동작되도록 하는 기능을 한다.
     * ★ 각각의 테스트는 의존성을 지니지 않아야 하므로 '@AfterEach'를 쓴다.
     */
    @AfterEach
    public void afterEach() {
        repository.clearStore();
    }

    @Test
    public void save() {
        Member member = new Member();
        member.setName("spring");

        repository.save(member);

        /**
         * findById 메서드는 Optional<V>를 반환한다.
         * 이때, get() 메서드로 내부 데이터를 반환 받을 수 있다.
         */
        Member result = repository.findById(member.getId()).get();

        // member가 result와 동일한지 확인한다
        assertThat(member).isEqualTo(result);
    }

    @Test
    public void findByName() {
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2);

        Member result = repository.findByName("spring1").get();

        assertThat(result).isEqualTo(member1);
    }

    @Test
    public void findAll() {
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2);

        List<Member> result = repository.findAll();

        // 전체 데이터의 개수가 2개인지 확인
        assertThat(result.size()).isEqualTo(2);
    }
}
