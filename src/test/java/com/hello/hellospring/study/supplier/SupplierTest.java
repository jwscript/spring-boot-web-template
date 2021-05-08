package com.hello.hellospring.study.supplier;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.function.Supplier;

public class SupplierTest {
    @Test
    @DisplayName("익명클래스를 사용하여 Supplier를 구현하고 정상 동작 확인")
    void supplierTest() {
        Supplier<String> supplyHello = new Supplier<String>() {
            @Override
            public String get() {
                return "Hello";
            }
        };

        Assertions.assertThat(supplyHello.get()).isEqualTo("Hello");
    }

    @Test
    @DisplayName("Lambda를 사용하여 Supplier를 구현하고 정상 동작 확인")
    void supplierTestWithLambda() {
        Supplier<String> supplyHello = () -> "Hello";

        Assertions.assertThat(supplyHello.get()).isEqualTo("Hello");
    }
}
