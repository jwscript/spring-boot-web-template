package com.hello.hellospring.study.future;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.concurrent.*;

public class FutureTest {
    /**
     * 자바 5부터는 미래 시점의 결과를 얻을 수 있는 Future Interface를 제공한다.
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @Test
    @DisplayName("Future class를 사용한 Future 테스트")
    void futureTest() throws ExecutionException, InterruptedException {
        // Single thread 생성
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        /*
         * ★ Callable 구현체를 submit으로 전달하면 인자로 전달된 Callable 구현체를 수행한다.
         * submit 메서드가 호출되었을 떄 Future 객체가 리턴되지만, 그 시점에 Future 객체에는 값이 있는 것은 아니다.
         */
        Future<Integer> future = executorService.submit(() -> {
            System.out.println("[1] Starting runnable");
            int sum = 2;
            Thread.sleep(3000);

            return sum;
        });

        System.out.println(LocalTime.now() + " [2] Waiting the task done");

        /*
         * ★ future.get() 메서드는 Future 객체에 어떤 값이 설정될 때까지 기다린다.
         * submit() 메서드에 전달된 Callable 구현체가 어떤 값을 리턴하면 그 값을 Future에 설정하고,
         * future.get() 메서드에 의해 그 값을 받아온다.
         */
        Integer result = future.get();
        System.out.println(LocalTime.now() + " [3] Result : " + result);
        Assertions.assertThat(result).isEqualTo(2);
    }

    @Test
    @DisplayName("CompletableFuture class를 사용한 Future 테스트")
    void futureTestWithCompletableFuture() throws ExecutionException, InterruptedException {
        // Future 객체를 생성한다. 이때, 미리 Generic으로 Integer 값을 받을 Future 객체임을 명시.
        CompletableFuture<Integer> future = new CompletableFuture<>();

        /*
         * Executors.newCachedThreadPool() 메서드는 Thread pool을 생성한다.
         * ★ Callable 구현체를 submit으로 전달하면 대기중인 Thread가 실행된다.
         */
        Executors.newCachedThreadPool().submit(() -> {
            System.out.println(LocalTime.now() + " [1] Doing something");
            int sum = 2;
            Thread.sleep(3000);
            future.complete(sum); // Future의 complete(data) 메서드로 다른 쓰레드로 전달할 데이터를 저장한다.
            return null;
        });

        System.out.println(LocalTime.now() + " [2] Waiting the task done");
        Integer result = future.get(); // Future 객체에 값이 들어오면(set) future.get() 메서드가 그 값을 가져온다.
        System.out.println(LocalTime.now() + " [3] Result : " + result);

        Assertions.assertThat(result).isEqualTo(2);
    }

    @Test
    @DisplayName("Future class를 사용하여 get 메서드에 Timeout 전달한 Future 테스트")
    void futureTestWithTimeout() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        Future<Integer> future = executorService.submit(() -> {
            System.out.println(LocalTime.now() + " [1] Starting runnable");
            int sum = 2;
            Thread.sleep(4000);
            System.out.println(LocalTime.now() + " Exiting runnable");
            return sum;
        });

        System.out.println(LocalTime.now() + " [2] Waiting the task done");
        Integer result = null;

        try {
            // future.get()에서 첫번째 파라미터는 숫자, 두번째 파라미터는 단위를 의미한다.
            future.get(2000, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            System.out.println(LocalTime.now() + " InterruptedException !!");
            e.printStackTrace();
        } catch (ExecutionException e) {
            System.out.println(LocalTime.now() + " ExecutionException !!");
            e.printStackTrace();
        } catch (TimeoutException e) {
            System.out.println(LocalTime.now() + " [3] TimeoutException !!");
            e.printStackTrace();
        }
        System.out.println(LocalTime.now() + " [4] Result : " + result);
    }
}
