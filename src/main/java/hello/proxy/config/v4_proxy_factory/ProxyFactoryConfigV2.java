package hello.proxy.config.v4_proxy_factory;

import hello.proxy.app.v1_interface_and_class.*;
import hello.proxy.app.v2_class_without_interface.OrderControllerV2;
import hello.proxy.app.v2_class_without_interface.OrderRepositoryV2;
import hello.proxy.app.v2_class_without_interface.OrderServiceV2;
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
public class ProxyFactoryConfigV2 {
    @Bean
    public OrderRepositoryV2 orderRepositoryV2(LogTrace logTrace) {
        OrderRepositoryV2 orderRepositoryV2 = new OrderRepositoryV2();
        return (OrderRepositoryV2) getProxyObject(logTrace, orderRepositoryV2);
    }

    @Bean
    public OrderServiceV2 orderServiceV2(LogTrace logTrace, OrderRepositoryV2 orderRepository) {
        OrderServiceV2 orderServiceV2 = new OrderServiceV2(orderRepository);
        return (OrderServiceV2) getProxyObject(logTrace, orderServiceV2);
    }

    @Bean
    public OrderControllerV2 orderControllerV2(LogTrace logTrace, OrderServiceV2 orderServiceV2) {
        OrderControllerV2 orderControllerV2 = new OrderControllerV2(orderServiceV2);
        return (OrderControllerV2) getProxyObject(logTrace, orderControllerV2);
    }

    private Object getProxyObject(LogTrace logTrace, Object target) {
        ProxyFactory proxyFactory = new ProxyFactory(target);

        proxyFactory.addAdvisor(new DefaultPointcutAdvisor(new ProxyFactoryConfigV2.MyPointcut(), new LogTraceAdvice(logTrace)));
        return proxyFactory.getProxy();
    }

    static class MyPointcut implements Pointcut {
        @Override
        public ClassFilter getClassFilter() {
            return ClassFilter.TRUE;
        }

        @Override
        public MethodMatcher getMethodMatcher() {
            return new ProxyFactoryConfigV2.MyMethodMatcher();
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
