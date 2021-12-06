package hello.proxy.jdk_dynamic;

import hello.proxy.jdk_dynamic.code.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Proxy;

@Slf4j
public class JdkDynamicProxyTest {
    @Test
    void dynamicA() {
        AInterface target = new AImpl();
        TimeInvocationHandler handler = new TimeInvocationHandler(target);

        AInterface proxy = (AInterface) Proxy.newProxyInstance(
                AInterface.class.getClassLoader()
                , new Class[]{AInterface.class} // 인터페이스는 implements 로 여러개 구현 가능하기 때문에, 배열
                , handler // 프록시가 사용해야 되는 로직을 넣어줌
        );

        // handler 의 invoke() 메소드를 실행하게 된다.
        proxy.call();
        log.info("targetClass={}", target.getClass());
        log.info("proxyClass={}", proxy.getClass());
    }

    @Test
    void dynamicB() {
        BInterface target = new BImpl();
        TimeInvocationHandler handler = new TimeInvocationHandler(target);

        BInterface proxy = (BInterface) Proxy.newProxyInstance(
                BInterface.class.getClassLoader()
                , new Class[]{BInterface.class} // 인터페이스는 implements 로 여러개 구현 가능하기 때문에, 배열
                , handler // 프록시가 사용해야 되는 로직을 넣어줌
        );

        // handler 의 invoke() 메소드를 실행하게 된다.
        proxy.call();
        log.info("targetClass={}", target.getClass());
        log.info("proxyClass={}", proxy.getClass());
    }
}
