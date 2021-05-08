package com.hello.hellospring.study.function;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

public class FunctionTest {

    private final List<String> listOfStrings =
            Arrays.asList("Black", "Blue", "", "Red", "White", "Pink", "Purple");

    @Test
    @DisplayName("익명클래스를 사용하여 Function을 구현하고 정상 동작 확인")
    void functionTest() {
        // 문자열의 길이를 반환하는 Function 타입 객체
        Function<String, Integer> getLength = new Function<String, Integer>() {
            @Override
            public Integer apply(String s) {
                return s.length();
            }
        };

        List<Integer> list = map(listOfStrings, getLength);

        System.out.println("list = " + list);
    }

    @Test
    @DisplayName("Lambda를 사용하여 Function을 구현하고 정상 동작 확인")
    void functionTestWithLambda() {
        Function<String, Integer> getLength = (String s) -> s.length();

        List<Integer> list = map(listOfStrings, getLength);

        System.out.println("list = " + list);
    }

    /**
     * 리스트의 각 요소의 문자열의 길이를 계산해서 반환
     *
     * @param list
     * @param f
     * @param <T>
     * @param <R>
     * @return
     */
    public static <T, R> List<R> map(List<T> list, Function<T, R> f) {
        List<R> result = new ArrayList<>();
        for (T string : list) {
            result.add(f.apply(string));
        }

        return result;
    }
}
