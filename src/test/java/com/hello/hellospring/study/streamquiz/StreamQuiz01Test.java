/**
 * Stream 실전 연습
 * TODO: (추가 학습 필요) 7번과 8번에 사용된 reduce의 이해도가 좀 떨어짐.
 * <p>
 * filter는 기존 데이터를 그대로 유지한채 조건에 부합하는 것을 포함하면 그대로 전달하는 느낌.
 * map은 기존 데이터 덩어리에서 필요한 데이터를 뽑아내는 느낌.
 */
package com.hello.hellospring.study.streamquiz;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class StreamQuiz01Test {

    private List<Transaction> transactions;

    @BeforeEach
    public void beforeEach() {
        Trader raoul = new Trader("Raoul", "Cambridge");
        Trader mario = new Trader("Mario", "Milan");
        Trader alan = new Trader("Alan", "Cambridge");
        Trader brian = new Trader("Brian", "Cambridge");
        Trader charlie = new Trader("Charlie", "Washington");
        Trader yuki = new Trader("Yuki", "Tokyo");
        Trader won = new Trader("Won", "Seoul");

        transactions = Arrays.asList(
                new Transaction(raoul, 2011, 300),
                new Transaction(mario, 2012, 500),
                new Transaction(alan, 2011, 100),
                new Transaction(brian, 2012, 200),
                new Transaction(charlie, 2011, 400),
                new Transaction(yuki, 2012, 600),
                new Transaction(raoul, 2011, 900),
                new Transaction(won, 2012, 800),
                new Transaction(brian, 2011, 700),
                new Transaction(won, 2012, 1000)
        );
    }

    @Test
    @DisplayName("1. 2011년에 일어난 모든 거래(트랜잭션)를 찾아 오른차순으로 정리하기.")
    void StreamQuiz01() {
        transactions.stream()
                .filter(transaction -> transaction.getYear() == 2011)
                .sorted(Comparator.comparing(Transaction::getValue))
                .forEach(transaction -> System.out.println("transaction = " + transaction));
    }

    @Test
    @DisplayName("2. 거래자가 근무하는 모든 도시를 중복 없이 나열하기.")
    void StreamQuiz02() {
        transactions.stream()
                .map(transaction -> transaction.getTrader().getCity())
                .distinct()
                .forEach(city -> System.out.println("city = " + city));
    }

    @Test
    @DisplayName("3. 케임브리지에 근무하는 모든 거래자를 찾아서 이름순으로 정렬하기.")
    void StreamQuiz03() {
        transactions.stream()
                .map(Transaction::getTrader)
                .filter(trader -> trader.getCity().equals("Cambridge"))
                .distinct()
                .sorted(Comparator.comparing(Trader::getName))
                .forEach(trader -> System.out.println("trader name = " + trader.getName()));
    }

    @Test
    @DisplayName("4. 모든 거래자의 이름을 알파벳순으로 정렬해서 반환하기.")
    void StreamQuiz04() {
        transactions.stream()
                .map(transaction -> transaction.getTrader().getName())
                .distinct()
                .sorted()
                .forEach(traderName -> System.out.println("traderName = " + traderName));
    }

    @Test
    @DisplayName("5. 밀라노에 거래자가 있는지 확인하기.")
    void StreamQuiz05() {
        boolean isExist = transactions.stream()
                .anyMatch(transaction -> transaction.getTrader().getCity().equals("Milan"));

        Assertions.assertThat(isExist).isEqualTo(true);
    }

    @Test
    @DisplayName("6. 케임브리지에 거주하는 거래자의 모든 거래(트랜잭션)를 출력하기.")
    void StreamQuiz06() {
        transactions.stream()
                .filter(transaction -> transaction.getTrader().getCity().equals("Cambridge"))
                .distinct()
                .map(Transaction::getValue)
                .forEach(value -> System.out.println("value = " + value));
    }

    @Test
    @DisplayName("7. 전체 거래(트랜잭션) 중 최댓값은?")
    void StreamQuiz07() {
        Optional<Integer> biggestValue = transactions.stream()
                .map(Transaction::getValue)
                .reduce(Integer::max);

        System.out.println("biggestValue = " + biggestValue);

        // map을 통해 반환하게 될 값의 타입을 정확하게 명시.
        int biggestValue2 = transactions.stream()
                .mapToInt(Transaction::getValue)
                .reduce(Integer::max)
                .getAsInt();

        System.out.println("biggestValue2 = " + biggestValue2);
    }

    @Test
    @DisplayName("8. 전체 거래(트랜잭션) 중 최솟값은?")
    void StreamQuiz08() {
        Optional<Integer> smallestValue = transactions.stream()
                .map(Transaction::getValue)
                .reduce(Integer::min);

        System.out.println("smallestValue = " + smallestValue);
    }

    static class Trader {
        private final String name;
        private final String city;

        public Trader(String name, String city) {
            this.name = name;
            this.city = city;
        }

        public String getName() {
            return name;
        }

        public String getCity() {
            return city;
        }

        @Override
        public String toString() {
            return "Trader{" +
                    "name='" + name + '\'' +
                    ", city='" + city + '\'' +
                    '}';
        }
    }

    static class Transaction {
        private final Trader trader;
        private final int year;
        private final int value;

        public Transaction(Trader trader, int year, int value) {
            this.trader = trader;
            this.year = year;
            this.value = value;
        }

        public Trader getTrader() {
            return trader;
        }

        public int getYear() {
            return year;
        }

        public int getValue() {
            return value;
        }

        @Override
        public String toString() {
            return "Transaction{" +
                    "trader=" + trader +
                    ", year=" + year +
                    ", value=" + value +
                    '}';
        }
    }
}
