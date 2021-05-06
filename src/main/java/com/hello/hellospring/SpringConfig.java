package com.hello.hellospring;

import com.hello.hellospring.repository.*;
import com.hello.hellospring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;
import javax.sql.DataSource;

@Configuration
public class SpringConfig {
    @Bean
    public MemberService memberService() {
        return new MemberService(memberRepository());
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
     * Jpa, Spring-data-jpa를 사용할 때 쓰는 EntityManage 객체
     */
    private EntityManager em;

    /**
     * 스프링에서 EntityManage 객체를 이미 스프링 빈으로 가지고 있다.
     * Q) 어떻게 ????
     * @param em
     */
    @Autowired
    public SpringConfig(EntityManager em) {
        this.em = em;
    }

    @Bean
    public MemberRepository memberRepository() {
        /*
        // MemoryMemberRepository 사용 시
        return new MemoryMemberRepository();
         */

        /*
        // JdbcMemberRepository 사용 시
        return new JdbcMemberRepository(dataSource);

         */

        /*
        // JdbcTemplateMemberRepository 사용시
        return new JdbcTemplateMemberRepository(dataSource);
         */

        // JpaMemberRepository 사용시
        return new JpaMemberRepository(em);
    }
}
