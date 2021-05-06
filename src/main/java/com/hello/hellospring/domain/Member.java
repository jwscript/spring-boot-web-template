package com.hello.hellospring.domain;

import javax.persistence.*;

/**
 * @Entity 어노테이션을 붙임으로써 JPA가 관리하는 엔티티임을 명시
 */
@Entity
public class Member {
    /**
     * @Id 어노테이션은 PK를 지정해준다.
     * GenerationType.IDENTITY : DB가 알아서 생성해주는 번호 IDENTITY.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * @Column 어노테이션은 엔티티의 멤버변수와 실제 테이블의 컬럼명이 다를 때 매핑해주는 기능을 한다.
     */
    @Column(name = "name")
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
