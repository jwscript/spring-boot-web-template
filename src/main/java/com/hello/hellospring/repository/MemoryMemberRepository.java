package com.hello.hellospring.repository;

import com.hello.hellospring.domain.Member;

import java.util.*;

public class MemoryMemberRepository implements MemberRepository {

    /**
     * Map 타입의 공유변수를 쓸 때는 동시성 문제를 방지하기 위해 ConcurrentMap 타입을 쓰는게 좋다.
     * (여기서는 테스트이니 그냥 쓴다)
     */
    private static Map<Long, Member> store = new HashMap<>();
    private static long sequence = 0L;

    @Override
    public Member save(Member member) {
        // 저장할 멤버 데이터에 시퀀스를 id로 추가
        member.setId(++sequence);

        // store 변수에 추가
        store.put(member.getId(), member);

        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        /**
         * store.get(id)가 null인 경우를 방지하기 위해
         * Optional로 감싸서 반환한다.
         */
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public Optional<Member> findByName(String name) {
        /**
         * 1. values()
         * > return : Optional<V>
         * Map을 순회하기 위한 방법 중 values를 사용.
         * (3가지 메서드로 entrySet, keySet, values가 있음)
         * 참고 1) entrySet()은 key, value가 모두 필요한 경우
         * 참고 2) keySet()은 key만 필요한 경우
         * 참고 3) values()는 value만 필요한 경우
         *
         * 2. stream()
         * 하나씩 반복문을 돌려준다고 보면 될듯?
         *
         * 3. findAny()
         * > return : Optional<T>
         */
        return store.values().stream()
                .filter(member -> member.getName().equals(name)) // name과 동일한게 있는지
                .findAny(); // 하나라도 데이터가 찾아지면 반환
    }

    @Override
    public List<Member> findAll() {
        return new ArrayList<>(store.values());
    }
}
