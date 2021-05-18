package com.hello.hellospring.study.threadSafe;

public class ThreadSafeTest {
}

/**
 * 1. Singleton 패턴의 구현 방법 중 가장 일반적인 방법. (Thread Safe X)
 * - 특징
 * Lazy Initialization 이라고도 할 수 있다.
 * ★ 멀티 스레드 환경에서 Thread Safe를 보장할 수 없다.
 */
class Singleton {
    private static Singleton instance;

    private Singleton() {
    }

    public static Singleton getInstance() {
        if (instance == null) {
            instance = new Singleton();
        }
        return instance;
    }
}

/**
 * 2. Singleton 패턴의 구현 방법 중 Early Loading을 사용한 방법. (Thread Safe X)
 * - 특징
 * SingletonWithEarlyLoading 클래스가 로드될 때, SingletonWithEarlyLoading 인스턴스가 생성된다.
 * ★ 클라이언트에서 인스턴스를 사용하지 않더라도 SingletonWithEarlyLoading 클래스가 로드될 때, 무조건 인스턴스가 생성된다는 것이 단점.
 * <p>
 * - 주의 사항 !!!
 * 만약, SingletonWithEarlyLoading 클래스에서 다른 static 메서드가 존재하지 않고, SingletonWithEarlyLoading.getInstance()를 호출하는 곳이 어디에도 없다면?
 * SingletonWithEarlyLoading 클래스는 로드되지 않으므로, 인스턴스는 생성되고 있지 않다.
 * 하지만, SingletonWithEarlyLoading 클래스에서 다른 static 메서드가 존재하고, 다른곳에서 사용되고 있다면
 * SingletonWithEarlyLoading 클래스는 로드되므로, 인스턴스는 무조건 생성된다.
 * (참고: https://yaboong.github.io/design-pattern/2018/09/28/thread-safe-singleton-patterns/)
 */
class SingletonWithEarlyLoading {
    private static SingletonWithEarlyLoading instance = new SingletonWithEarlyLoading();

    private SingletonWithEarlyLoading() {
    }

    public static SingletonWithEarlyLoading getInstance() {
        return instance;
    }
}

/**
 * 3. Singleton 패턴의 구현 방법 중 Double Checked Locking을 사용한 방법. (★ Thread Safe)
 * - 특징
 * getInstance() 메서드에 synchronized를 붙이지 않음으로써, 동기화 오버헤드를 줄임.
 * instance가 null 인지 체크하고, null 인 경우 동기화 블럭에 진입한다.
 * - 단점
 * 최초 객체가 생성된 이후에는 동기화 블럭에 진입하지 않지만 아래와 같은 단점이 있다.
 * Thread A가 instance의 생성을 완료하기 전에 메모리 공간에 할당이 가능하기 때문에
 * Thread B가 아직 메모리 공간에만 할당 된 instance를 사용하려고 할 수 있다.
 * (오동작 발생 가능)
 */
class SingletonWithDcl {
    private volatile static SingletonWithDcl instance;

    private SingletonWithDcl() {
    }

    public static SingletonWithDcl getInstance() {
        if (instance == null) {
            synchronized (SingletonWithDcl.class) {
                if (instance == null) {
                    instance = new SingletonWithDcl();
                }
            }
        }

        return instance;
    }
}

/**
 * 4. Singleton 패턴의 구현 방법 중 LazyHolder를 사용한 방법. (★ Thread Safe)
 * (★ 현재 가장 완벽한 것으로 평가 받고 있음.)
 * <p>
 * - 특징
 * synchronized를 사용하지 않는다.
 * java 버전에 상관없다.
 * - 설명
 * ★ 객체가 필요할 때까지 초기화를 미루는 것.
 * SingletonWithLazyHolder 클래스에는 LazyHolder 클래스의 변수가 없기 때문에
 * SingletonWithLazyHolder 클래스 로딩 시 LazyHolder 클래스를 초기화하지 않는다.
 * LazyHolder 클래스는 SingletonWithLazyHolder 클래스의 getInstance() 메서드가 호출되면
 * LazyHolder.INSTACNE를 참조되면서 LazyHolder 클래스의 초기화가 진행된다.
 */
class SingletonWithLazyHolder {
    private SingletonWithLazyHolder() {
    }

    public static SingletonWithLazyHolder getInstance() {
        return LazyHolder.INSTANCE;
    }

    // 별도의 내부 클래스 생성
    private static class LazyHolder {
        private static final SingletonWithLazyHolder INSTANCE = new SingletonWithLazyHolder();
    }
}

/**
 * TODO: 추가적으로 더 알고 싶은 경우 참고할 링크
 * https://yaboong.github.io/design-pattern/2018/09/28/thread-safe-singleton-patterns/
 */