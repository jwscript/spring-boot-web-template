/**
 * ★ Compare의 핵심은 정렬의 '기준'을 설계하는 것이다.
 * <p>
 * Comparable : 기본 정렬 기능을 추가해주는 것.
 * Comparator : 기본 정렬 기능이 아닌 커스텀 정렬을 만들어 두는 것. (언제든 꺼내 쓸 수 있게)
 */
package com.hello.hellospring.study.compare;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.*;

public class CompareTest {
    @Test
    @DisplayName("Comparable을 구현한 특정 클래스의 기본 정렬 compareTo 구현 및 동작 확인")
    void comparatorTest() {
        Student[] students = {
                new Student(100, "abc"),
                new Student(300, "aaa"),
                new Student(400, "ccc"),
                new Student(200, "bbb"),
        };

        Arrays.sort(students);
        System.out.println(Arrays.toString(students));
    }

    @Test
    @DisplayName("Comparator를 구현한 커스텀 클래스의 compare 구현 및 동작 확인")
    void comparableTest() {
        Student[] students = {
                new Student(100, "abc"),
                new Student(300, "aaa"),
                new Student(400, "ccc"),
                new Student(200, "bbb"),
        };

        Arrays.sort(students, new NameAscending());
        System.out.println(Arrays.toString(students));
    }

    @Test
    @DisplayName("Stream의 sort에서의 comparing 구현 및 동작 확인")
    void comparingTest() {
        List<Student> students = Arrays.asList(
                new Student(100, "abd"),
                new Student(700, "ddd"),
                new Student(400, "abc"),
                new Student(200, "ccc"),
                new Student(800, "bbb"),
                new Student(600, "cba"),
                new Student(300, "aaa"),
                new Student(500, "eee")
        );

        // 이름 오름차순 정렬 - 0번째 방법 (커스텀 Comparator 사용)
        students.stream()
                .sorted(new NameAscending())
                .forEach(student -> {
                    System.out.println(student.toString());
                });

        // 이름 오름차순 정렬 - 1번째 방법
        students.stream()
                .sorted(Comparator.comparing(Student::getScore))
                .forEach(student -> {
                    System.out.println(student.toString());
                });

        // 이름 오름차순 정렬 - 2번째 방법
        students.stream()
                .sorted(Comparator.comparing((student) -> student.score))
                .forEach(student -> {
                    System.out.println(student.toString());
                });

        // 점수 내림차순 정렬
        students.stream()
                .sorted(Comparator.comparing(Student::getScore).reversed())
                .forEach(student -> {
                    System.out.println(student.toString());
                });

        // 이름 오름차순 정렬 후, 점수 오름차순 정렬
        students.stream()
                .sorted(Comparator.comparing(Student::getName)
                        .thenComparing(Student::getScore))
                .forEach(student -> {
                    System.out.println(student.toString());
                });
    }

    static class Student implements Comparable {
        int score;
        String name;

        public Student(int score, String name) {
            this.score = score;
            this.name = name;
        }

        public int getScore() {
            return score;
        }

        public String getName() {
            return name;
        }

        @Override
        public String toString() {
            return "Student{" +
                    "score=" + score +
                    ", name='" + name + '\'' +
                    '}';
        }

        /**
         * 객체 간의 기본 정렬기능을 사용하기 위해
         * Comparable 인터페이스의 compareTo를 오버라이딩 한다.
         *
         * @param o
         * @return
         */
        @Override
        public int compareTo(Object o) {
            Student nextStudent = (Student) o;

            /**
             * return 값에 따른 결과
             * 1) 양수 : 좌우의 값을 변경한다. (여기서는 this.score가 nextStudent.score가 커서 양수가 나왔므로 '내림차순' 처리)
             * 2) 0 : 변경 없음.
             * 3) 음수 : 변경 없음. (여기서는 this.score가 nextStudent.score보다 작아서 음수가 나왔으므로 '오름차순' 처리)
             */
            return this.score - nextStudent.score;
        }
    }

    /**
     * 객체 간의 기본 정렬이 아닌, 커스텀 정렬을 추가하기 위해
     * Comparator 인터페이스의 compare를 오버라이딩 한다.
     */
    static class NameAscending implements Comparator {
        @Override
        public int compare(Object o1, Object o2) {
            Student student1 = (Student) o1;
            Student student2 = (Student) o2;

            /**
             * String.compareTo가 이미 있음.
             * this(현재것)을 기준으로 다음것을 넣었으므로 기본값은 오름차순일 것임.
             * ※ 참고: return student2.name.compareTo(student1.name); 을 넣는 사용하면 내림차순이 될 것임.
             */
            return student1.name.compareTo(student2.name);
        }
    }
}