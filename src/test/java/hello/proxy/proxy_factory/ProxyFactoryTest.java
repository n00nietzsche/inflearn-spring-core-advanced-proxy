package hello.proxy.proxy_factory;

import hello.proxy.common.advice.TimeAdvice;
import hello.proxy.common.service.ConcreteService;
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

    @Test
    @DisplayName("구체 클래스만 있으면, CGLIB를 사용한다.")
    void concreteProxy() {
        ConcreteService target = new ConcreteService();
        // target 을 필드로 주입받진 않더라도, 생성자에서 주입 받음.
        ProxyFactory proxyFactory = new ProxyFactory(target);
        proxyFactory.addAdvice(new TimeAdvice());

        ConcreteService proxy = (ConcreteService) proxyFactory.getProxy();

        proxy.call();

        // AopUtils 클래스의 메서드를 통해 다양한 검증이 가능한데,
        // 이는 오직 ProxyFactory 기능을 이용해 생성한 프록시 객체에만 적용 가능하다.
        Assertions.assertThat(AopUtils.isAopProxy(proxy)).isTrue();
        Assertions.assertThat(AopUtils.isJdkDynamicProxy(proxy)).isFalse();
        Assertions.assertThat(AopUtils.isCglibProxy(proxy)).isTrue();

        log.info("targetClass={}", target.getClass());
        // `hello.proxy.common.service.ServiceImpl$$EnhancerBySpringCGLIB$$97c1d79e`
        // 인터페이스가 없는 구현체를 넣어 `CGLIB` 기술이 적용되었다.
        log.info("proxyClass={}", proxy.getClass());
    }

    @Test
    @DisplayName("ProxyTargetClass 옵션을 사용하면, 인터페이스가 있어도 CGLIB를 사용하고, 클래스 기반 프록시를 사용한다.")
    void proxytargetClass() {
        // 인터페이스가 있는 경우임을 강조
        ServiceInterface target = new ServiceImpl();

        // target 을 필드로 주입받진 않더라도, 생성자에서 주입 받음.
        ProxyFactory proxyFactory = new ProxyFactory(target);
        // `setProxyTargetClass()`를 설정하면, CGLIB 기반으로 프록시를 만든다.
        proxyFactory.setProxyTargetClass(true);
        proxyFactory.addAdvice(new TimeAdvice());

        ServiceInterface proxy = (ServiceInterface) proxyFactory.getProxy();

        proxy.find();
        proxy.save();

        // AopUtils 클래스의 메서드를 통해 다양한 검증이 가능한데,
        // 이는 오직 ProxyFactory 기능을 이용해 생성한 프록시 객체에만 적용 가능하다.
        Assertions.assertThat(AopUtils.isAopProxy(proxy)).isTrue();
        Assertions.assertThat(AopUtils.isJdkDynamicProxy(proxy)).isFalse();
        Assertions.assertThat(AopUtils.isCglibProxy(proxy)).isTrue();

        log.info("targetClass={}", target.getClass());
        // `hello.proxy.common.service.ServiceImpl$$EnhancerBySpringCGLIB$$97c1d79e`
        // 인터페이스가 있는 구현체지만, `setProxyTargetClass(true)` 옵션을 사용하여, CGLIB에 의해 프록시 객체가 생성되었다.
        log.info("proxyClass={}", proxy.getClass());
    }
}
