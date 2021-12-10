package hello.proxy.config.v4_proxy_factory;

import hello.proxy.app.v1_interface_and_class.*;
import hello.proxy.config.v4_proxy_factory.advice.LogTraceAdvice;
import hello.proxy.trace.logtrace.LogTrace;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.ClassFilter;
import org.springframework.aop.MethodMatcher;
import org.springframework.aop.Pointcut;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Method;

@Slf4j
@Configuration
public class ProxyFactoryConfigV1 {
    @Bean
    public OrderRepositoryV1 orderRepositoryV1(LogTrace logTrace) {
        OrderRepositoryV1 orderRepositoryV1 = new OrderRepositoryV1Impl();
        return (OrderRepositoryV1) getProxyObject(logTrace, orderRepositoryV1);
    }

    @Bean
    public OrderServiceV1 orderServiceV1(LogTrace logTrace, OrderRepositoryV1 orderRepository) {
        OrderServiceV1 orderServiceV1 = new OrderServiceV1Impl(orderRepository);
        return (OrderServiceV1) getProxyObject(logTrace, orderServiceV1);
    }

    @Bean
    public OrderControllerV1 orderControllerV1(LogTrace logTrace, OrderServiceV1 orderServiceV1) {
        OrderControllerV1 orderControllerV1 = new OrderControllerV1Impl(orderServiceV1);
        return (OrderControllerV1) getProxyObject(logTrace, orderControllerV1);
    }

    private Object getProxyObject(LogTrace logTrace, Object target) {
        ProxyFactory proxyFactory = new ProxyFactory(target);

        proxyFactory.addAdvisor(new DefaultPointcutAdvisor(new MyPointcut(), new LogTraceAdvice(logTrace)));
        return proxyFactory.getProxy();
    }

    static class MyPointcut implements Pointcut {
        @Override
        public ClassFilter getClassFilter() {
            return ClassFilter.TRUE;
        }

        @Override
        public MethodMatcher getMethodMatcher() {
            return new MyMethodMatcher();
        }
    }

    static class MyMethodMatcher implements MethodMatcher {

        @Override
        public boolean matches(Method method, Class<?> targetClass) {
            return !method.getName().equals("noLog");
        }

        @Override
        public boolean isRuntime() {
            return false;
        }

        @Override
        public boolean matches(Method method, Class<?> targetClass, Object... args) {
            return false;
        }
    }
}
