package hello.proxy.proxy_factory;

import hello.proxy.common.advice.TimeAdvice;
import hello.proxy.common.service.ServiceImpl;
import hello.proxy.common.service.ServiceInterface;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.AopUtils;

@Slf4j
public class ProxyFactoryTest {
    @Test
    @DisplayName("인터페이스가 있으면 JDK 동적 프록시를 사용한다.")
    void interfaceProxy() {
        // 인터페이스가 있는 경우임을 강조
        ServiceInterface target = new ServiceImpl();

        // target 을 필드로 주입받진 않더라도, 생성자에서 주입 받음.
        ProxyFactory proxyFactory = new ProxyFactory(target);
        proxyFactory.addAdvice(new TimeAdvice());

        ServiceInterface proxy = (ServiceInterface) proxyFactory.getProxy();

        proxy.find();
        proxy.save();

        // AopUtils 클래스의 메서드를 통해 다양한 검증이 가능한데,
        // 이는 오직 ProxyFactory 기능을 이용해 생성한 프록시 객체에만 적용 가능하다.
        Assertions.assertThat(AopUtils.isAopProxy(proxy)).isTrue();
        Assertions.assertThat(AopUtils.isJdkDynamicProxy(proxy)).isTrue();
        Assertions.assertThat(AopUtils.isCglibProxy(proxy)).isFalse();

        log.info("targetClass={}", target.getClass());
        // proxy의 클래스로 com.sun.proxy.$Proxy10이 나온다.
        // 인터페이스가 있어서 `JDK 동적 프록시`가 적용되었다.
        log.info("proxyClass={}", proxy.getClass());
    }
}
