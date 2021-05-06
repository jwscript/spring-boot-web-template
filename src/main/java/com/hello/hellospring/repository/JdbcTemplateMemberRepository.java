package com.hello.hellospring.repository;

import com.hello.hellospring.domain.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class JdbcTemplateMemberRepository implements MemberRepository {

    private final JdbcTemplate jdbcTemplate;

    /**
     * ★ 여기에 @Autowired를 붙이지 않아도 붙인것처럼 동작하는 이유?
     * SpringConfig.class 에서 JdbcTemplateMemberRepository의 스프링 빈을 생성하기 때문에 스프링 빈으로 관리되고,
     * 스프링 빈으로 관리되는 경우 생성자가 하나만 있으면 그 생성자에는 @Autowired가 붙어있지 않아도 붙은 것과 같이 동작한다.
     * @param dataSource
     */
    public JdbcTemplateMemberRepository(DataSource dataSource) {
        /**
         * ※ JdbcTemplate는 DataSource 처럼 스프링에서 관리하는 객체를 주입받을 수 없다.
         * 그래서 스프링에서 관리하는 DataSource 타입의 객체를 주입받아서
         * 그것(dataSource)를 파라미터로 생성.
         */
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    /**
     * (소스는 직관적으로 되어 있는듯?)
     * insert할 테이블과 자동으로 증가하는 sequence 컬럼으로 id를 지정하고
     * 데이터 전달을 Map 타입의 변수로 처리.
     * @param member
     * @return
     */
    @Override
    public Member save(Member member) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("member").usingGeneratedKeyColumns("id");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("name", member.getName());

        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));
        member.setId(key.longValue());
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        List<Member> result = jdbcTemplate.query("SELECT * FROM member WHERE id = ?", memberRowMapper(), id);
        return result.stream().findAny();
    }

    @Override
    public Optional<Member> findByName(String name) {
        List<Member> result = jdbcTemplate.query("SELECT * FROM member WHERE name = ?", memberRowMapper(), name);
        return result.stream().findAny();
    }

    @Override
    public List<Member> findAll() {
        return jdbcTemplate.query("SELECT * FROM member", memberRowMapper());
    }

    private RowMapper<Member> memberRowMapper() {
        return (rs, rowNum) -> {
            System.out.println("rowNum = " + rowNum);
            Member member = new Member();
            member.setId(rs.getLong("id"));
            member.setName(rs.getString("name"));
            return member;
        };
    }
}
