package com.hello.hellospring.service;

import com.hello.hellospring.domain.Member;
import com.hello.hellospring.repository.MemoryMemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MemberServiceTest {

    MemberService memberService;
    MemoryMemberRepository memberRepository;

    /**
     * @BeforeEach 어노테이션
     * 테스트가 실행되기 전 아래 메서드가 동작되도록 하는 기능을 한다.
     * ★ 각각의 테스트는 의존성을 지니지 않아야 하므로 '@BeforeEach'를 쓴다.
     */
    @BeforeEach
    public void beforeEach() {
        memberRepository = new MemoryMemberRepository();
        memberService = new MemberService(memberRepository);
    }

    @AfterEach
    public void afterEach() {
        /**
         * 현재 MemoryMemberRepository.store 를 static으로 공유하기 때문에
         * 각자 객체를 생성했더라도 clearStore 메서드를 통해 초기화되는 객체(store)는 동일하다.
         */
        memberRepository.clearStore();
    }

    @Test
    void join() {
        // given
        Member member = new Member();
        member.setName("hello");

        // when
        Long saveId = memberService.join(member);

        // then
        Member findMember = memberService.findOne(saveId).get();
        assertThat(member.getName()).isEqualTo(findMember.getName());
    }

    @Test
    void joinDuplicateException() {
        // given
        Member member1 = new Member();
        member1.setName("spring");

        Member member2 = new Member();
        member2.setName("spring");

        // when
        memberService.join(member1);
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> {
            memberService.join(member2);
        });

        // then
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");

        /*
        // assertThrows를 쓰지 않았던 이전에는 아래와 같이 try..catch 방식을 사용했다.
        try {
            memberService.join(member2); // 중복 이름으로 오류가 발생해야 한다 !
            fail();
        } catch (IllegalStateException e) {
            assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
        }
         */
    }

    @Test
    void findMembers() {
    }

    @Test
    void findOne() {
    }
}