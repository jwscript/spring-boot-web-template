package com.hello.hellospring;

import com.hello.hellospring.aop.TimeTraceAop;
import com.hello.hellospring.repository.*;
import com.hello.hellospring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {
    @Bean
    public MemberService memberService() {
        /*
        // Spring-data-jpa 외의 4가지 방법에 사용
        return new MemberService(memberRepository());
         */

        return new MemberService(memberRepository);
    }

    /**
     * Jdbc, JdbcTemplate를 사용할 때 쓰는 Datasource 객체
     */
    /*
    // TODO: Jdbc, JdbcTemplate를 사용할려면 주석 해제 할 것.
    private DataSource dataSource;

    @Autowired
    public SpringConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }
     */

    /**
     * Jpa를 사용할 때 쓰는 EntityManage 객체
     */
    /*
    // TODO: Jpa를 사용할려면 주석 해제 할 것.
    private EntityManager em;

    @Autowired
    public SpringConfig(EntityManager em) {
        this.em = em;
    }
     */

    private final MemberRepository memberRepository;

    /**
     * MemberRepository가 스프링 빈으로 설정한 곳이 없음에도 주입되는 것은
     * spring-data-jpa가 JpaRepository와 MemberRepository를 상속받은 인터페이스의 구현체를 만들어서
     * 자동으로 스프링 빈 객체로 등록해놓기 때문이다.
     */
    @Autowired
    public SpringConfig(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

//    @Bean
//    public MemberRepository memberRepository() {
//        /*
//        // MemoryMemberRepository 사용 시
//        return new MemoryMemberRepository();
//         */
//
//        /*
//        // JdbcMemberRepository 사용 시
//        return new JdbcMemberRepository(dataSource);
//         */
//
//        /*
//        // JdbcTemplateMemberRepository 사용시
//        return new JdbcTemplateMemberRepository(dataSource);
//         */
//
//        /*
//        // JpaMemberRepository 사용시
//        return new JpaMemberRepository(em);
//         */
//    }
}
