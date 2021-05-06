package com.hello.hellospring.service;

import com.hello.hellospring.domain.Member;
import com.hello.hellospring.repository.MemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @SpringBootTest 어노테이션은 이 테스트가 스프링 부트에 의존하는 것을 의미. (스프링 부트 실행 후 테스트하게 됨)
 * @Transactional 어노테이션은 실행한 테스트를 DB에 반영하지 않도록 Rollback 처리 해준다. (즉, 다음 테스트를 반복해도 이전 테스트에 영향을 받지 않는다.)
 * ㄴ 단, 테스트 클래스의 메서드 단위에서 @Commit 과 같은 어노테이션을 쓰면 Rollback이 아닌 Commit이 우선된다.
 */
@SpringBootTest
@Transactional
class MemberServiceIntegrationTest {

    @Autowired
    MemberService memberService;

    @Autowired
    MemberRepository memberRepository;

    @Test
    void join() {
        // given
        Member member = new Member();
        member.setName("hello3");

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