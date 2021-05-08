package com.hello.hellospring.study.consumer;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class ConsumerTest {

    private final List<Integer> listOfInteger =
            Arrays.asList(3, 5, 1, 7, 4, 6, 2, 8, 9);

    @Test
    @DisplayName("익명클래스를 사용하여 Consumer를 구현하고 정상 동작 확인")
    void consumerTest() {
        Consumer<Integer> printInteger = new Consumer<Integer>() {
            @Override
            public void accept(Integer i) {
                System.out.println("integer = " + i);
            }
        };

        forEach(listOfInteger, printInteger);
    }

    @Test
    @DisplayName("Lambda를 사용하여 Consumer를 구현하고 정상 동작 확인")
    void consumerTestWithLambda() {
        Consumer<Integer> printInteger = (Integer i) -> System.out.println("integer = " + i);

        forEach(listOfInteger, printInteger);
    }

    /**
     * 리스트의 각 요소를 출력처리
     *
     * @param list
     * @param c
     * @param <T>
     */
    public static <T> void forEach(List<T> list, Consumer<T> c) {
        for (T integer : list) {
            c.accept(integer);
        }
    }
}
