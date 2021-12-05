package hello.proxy.jdk_dynamic;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@Slf4j
public class ReflectionTest {
    @Test
    void reflection0() {
        Hello target = new Hello();

        // 공통 로직 1 시작
        log.info("start");
        String result1 = target.callA();
        log.info("result={}", result1);
        // 공통 로직 1 종료

        // 공통 로직 2 시작
        log.info("start");
        String result2 = target.callB(); // 호출하는 메서드만 다를 뿐 전체 코드는 완전 같다.
        log.info("result={}", result2);
        // 공통 로직 2 종료
    }

    @Test
    void reflection1() throws Exception {
        // 클래스 정보
        Class<?> classHello = Class.forName("hello.proxy.jdk_dynamic.ReflectionTest$Hello");

        Hello target = new Hello();

        // callA 메서드 정보 얻기
        Method methodCallA = classHello.getMethod("callA");

        // target 인스턴스에 있는 "callA"를 호출하게 됨
        Object result1 = methodCallA.invoke(target);
        log.info("result1={}", result1);

        // callB 메서드 정보 얻기
        Method methodCallB = classHello.getMethod("callB");

        // target 인스턴스에 있는 "callA"를 호출하게 됨
        Object result2 = methodCallB.invoke(target);
        log.info("result2={}", result2);
    }

    @Test
    void reflection2() throws Exception {
        // 클래스 정보
        Class<?> classHello = Class.forName("hello.proxy.jdk_dynamic.ReflectionTest$Hello");

        Hello target = new Hello();

        // callA 메서드 정보 얻기
        Method methodCallA = classHello.getMethod("callA");
        dynamicCall(methodCallA, target);

        // callB 메서드 정보 얻기
        Method methodCallB = classHello.getMethod("callB");
        dynamicCall(methodCallB, target);
    }

    private void dynamicCall(Method method, Object target) throws InvocationTargetException, IllegalAccessException {
        log.info("start");
        // 어떤 객체와 메서드 정보를 받고 받은 메서드를 실행한 결과를 result 에 저장한다.
        // 많은 추상화가 이루어졌다. 이 곳엔 어떤 메서드가, 어떤 객체가 올지 모른다.
        String result = (String) method.invoke(target);
        log.info("result={}", result);
    }

    @Slf4j
    static class Hello {
        public String callA() {
            log.info("callA");
            return "A";
        }

        public String callB() {
            log.info("callB");
            return "B";
        }
    }
}
