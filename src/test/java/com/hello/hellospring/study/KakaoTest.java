package com.hello.hellospring.study;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class KakaoTest {
    static int orderBy = 1;
    static int orderDirection = 1;

    @Test
    void kakaoTest01() {
        int pageSize = 2;
        int pageNumber = 1;
        int limit = pageSize;
        int offset = pageNumber * pageSize;

        List<Item> items = Arrays.asList(
                new Item("item1", "10", "20"),
                new Item("item2", "5", "25"),
                new Item("item3", "7", "30"),
                new Item("item4", "9", "35"),
                new Item("item5", "13", "15")
        );

        /**
         * orderBy 이름(0), orderDirection 오름차순(0) : 1 2 3 4 5
         * orderBy 이름(0), orderDirection 내림차순(1) : 5 4 3 2 1
         * orderBy 관련도(1), orderDirection 오름차순(0) : 2 3 4 1 5
         * orderBy 관련도(1), orderDirection 내림차순(1) : 5 1 4 3 2
         * orderBy 가격(2), orderDirection 오름차순(0) : 5 1 2 3 4
         * orderBy 가격(2), orderDirection 내림차순(1) : 4 3 2 1 5
         */

        // 방법 1. 별도의 파라미터 없이 orderBy, orderDirection 2가지를 사용하는 방식.
        Object collect = items.stream()
                .sorted(new CustomSorting())
                .skip(offset)
                .limit(limit)
                .map(obj -> ((Item) obj).getName())
                .collect(Collectors.toList());
        System.out.println("collect = " + collect);

        /*
        // 방법 2. orderBy를 분기 파라미터로 사용하는 방식.
        if (orderBy == 0) {
            items.stream()
                    .sorted(Comparator.comparing(Item::getName, orderDirection == 0 ? Comparator.naturalOrder() : Comparator.reverseOrder()))
                    .skip(offset)
                    .limit(limit)
                    .forEach(item -> {
                        System.out.println(item.getName());
                    });
        } else if (orderBy == 1) {
            items.stream()
                    .sorted(Comparator.comparing(Item::getRelevance, orderDirection == 0 ? Comparator.naturalOrder() : Comparator.reverseOrder()))
                    .skip(offset)
                    .limit(limit)
                    .forEach(item -> {
                        System.out.println(item.getName());
                    });
        } else {
            items.stream()
                    .sorted(Comparator.comparing(Item::getPrice, orderDirection == 0 ? Comparator.naturalOrder() : Comparator.reverseOrder()))
                    .skip(offset)
                    .limit(limit)
                    .forEach(item -> {
                        System.out.println(item.getName());
                    });
        }
         */

        /*
        // 방법 3. orderBy와 orderDirection을 분기 파라미터로 사용하는 방법
        if (orderBy == 0 && orderDirection == 0) {
            items.stream()
                    .sorted(Comparator.comparing(Item::getName))
                    .skip(offset)
                    .limit(limit)
                    .forEach(item -> {
                        System.out.println(item.getName());
                    });
        } else if (orderBy == 0 && orderDirection == 1) {
            items.stream()
                    .sorted(Comparator.comparing(Item::getName).reversed())
                    .skip(offset)
                    .limit(limit)
                    .forEach(item -> {
                        System.out.println(item.getName());
                    });
        } else if (orderBy == 1 && orderDirection == 0) {
            items.stream()
                    .sorted(Comparator.comparing(Item::getRelevance))
                    .skip(offset)
                    .limit(limit)
                    .forEach(item -> {
                        System.out.println(item.getName());
                    });
        } else if (orderBy == 1 && orderDirection == 1) {
            items.stream()
                    .sorted(Comparator.comparing(Item::getRelevance).reversed())
                    .skip(offset)
                    .limit(limit)
                    .forEach(item -> {
                        System.out.println(item.getName());
                    });
        } else if (orderBy == 2 && orderDirection == 0) {
            items.stream()
                    .sorted(Comparator.comparing(Item::getPrice))
                    .skip(offset)
                    .limit(limit)
                    .forEach(item -> {
                        System.out.println(item.getName());
                    });
        } else if (orderBy == 2 && orderDirection == 1) {
            items.stream()
                    .sorted(Comparator.comparing(Item::getPrice).reversed())
                    .skip(offset)
                    .limit(limit)
                    .forEach(item -> {
                        System.out.println(item.getName());
                    });
        }
         */
    }

    static class Item {
        String name;
        String relevance;
        String price;

        public Item(String name, String relevance, String price) {
            this.name = name;
            this.relevance = relevance;
            this.price = price;
        }

        public String getName() {
            return name;
        }

        public int getRelevance() {
            return Integer.parseInt(relevance);
        }

        public int getPrice() {
            return Integer.parseInt(price);
        }
    }

    @Test
    void kakaoTest02() {
        int session = 18;
        int[] startTime = new int[]{7, 3, 5, 4, 3, 2, 3, 1, 4, 3, 5, 2, 6, 3, 5, 1, 2, 3};
        int[] runningTime = new int[]{1, 2, 1, 1, 2, 3, 2, 1, 2, 3, 1, 3, 2, 3, 2, 1, 2, 1};

        int[][] sessionTime = new int[session][2];

        for (int i = 0; i < session; i++) {
            sessionTime[i][0] = startTime[i];
            sessionTime[i][1] = startTime[i] + runningTime[i];
        }

        final int[] processedTime = {0};

        long count = Arrays.stream(sessionTime)
                .sorted(Comparator.comparing((int[] arr) -> arr[1]).thenComparing((int[] arr) -> arr[0]))
                .filter(arr -> {
                    if (arr[0] >= processedTime[0]) {
                        processedTime[0] = arr[1];
                        return true;
                    }
                    return false;
                }).count();

        System.out.println("count = " + count);
    }

    static class CustomSorting implements Comparator {
        @Override
        public int compare(Object o1, Object o2) {
            Item item1 = (Item) o1;
            Item item2 = (Item) o2;

            String name1 = item1.getName();
            String name2 = item2.getName();

            int relevance1 = item1.getRelevance();
            int relevance2 = item2.getRelevance();

            int price1 = item1.getPrice();
            int price2 = item2.getPrice();

            int result = 0;

            if (orderBy == 0) {
                if (orderDirection == 0) {
                    return name1.compareTo(name2);
                } else {
                    return name2.compareTo(name1);
                }
            } else if (orderBy == 1) {
                if (orderDirection == 0) {
                    if (relevance1 > relevance2) result = 1;
                    else if (relevance1 == relevance2) result = 0;
                    else if (relevance1 < relevance2) result = -1;
                } else {
                    if (relevance1 > relevance2) result = -1;
                    else if (relevance1 == relevance2) result = 0;
                    else if (relevance1 < relevance2) result = 1;
                }
            } else if (orderBy == 2) {
                if (orderDirection == 0) {
                    if (price1 > price2) result = 1;
                    else if (price1 == price2) result = 0;
                    else if (price1 < price2) result = -1;
                } else {
                    if (price1 > price2) result = -1;
                    else if (price1 == price2) result = 0;
                    else if (price1 < price2) result = 1;
                }
            }

            return result;
        }
    }
}
