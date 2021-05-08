package com.hello.hellospring.study.predicate;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

public class PredicateTest {

    // 문자열 List 생성
    private final List<String> listOfStrings =
            new ArrayList<String>(Arrays.asList("Black", "", "", "Red", "White", "Blue", ""));

    @Test
    @DisplayName("익명클래스를 사용하여 Predicate를 구현하고 정상 동작 확인")
    void predicateTest() {
        // 비어있지 않은 문자열인지 확인하는 Predicate 타입 객체
        Predicate<String> nonEmptyStringPredicate = new Predicate<String>() {
            @Override
            public boolean test(String s) {
                return !s.isEmpty();
            }
        };

        // 비어있지 않은 문자열을 제거하는 filter 적용
        List<String> nonEmptyStringList = filter(listOfStrings, nonEmptyStringPredicate);

        System.out.println("nonEmptyStringList = " + nonEmptyStringList);

        // 공백을 포함하지 않는지 검증
        Assertions.assertThat(nonEmptyStringList).doesNotContain("");
    }

    @Test
    @DisplayName("Lambda를 사용하여 Predicate를 구현하고 정상 동작 확인")
    void predicateTestWithLambda() {
        // 비어있지 않은 문자열인지 확인하는 Predicate 타입 객체
        Predicate<String> nonEmptyStringPredicate = (String s) -> !s.isEmpty();

        // 비어있지 않은 문자열을 제거하는 filter 적용
        List<String> nonEmptyStringList = filter(listOfStrings, nonEmptyStringPredicate);

        System.out.println("nonEmptyStringList = " + nonEmptyStringList);

        // 공백을 포함하지 않는지 검증
        Assertions.assertThat(nonEmptyStringList).doesNotContain("");
    }

    /**
     * 비어있지 않은 문자열만 남기는 메서드
     *
     * @param list
     * @param predicate
     * @param <T>
     * @return
     */
    public static <T> List<T> filter(List<T> list, Predicate<T> predicate) {
        List<T> result = new ArrayList<T>();

        for (T string : list) {
            if (predicate.test(string)) {
                result.add(string);
            }
        }

        return result;
    }
}
