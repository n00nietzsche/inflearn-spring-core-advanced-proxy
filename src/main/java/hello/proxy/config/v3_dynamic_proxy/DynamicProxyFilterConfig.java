package hello.proxy.config.v3_dynamic_proxy;

import hello.proxy.app.v1_interface_and_class.*;
import hello.proxy.config.v3_dynamic_proxy.handler.LogTraceFilterHandler;
import hello.proxy.trace.logtrace.LogTrace;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Proxy;

@Configuration
public class DynamicProxyFilterConfig {
    private final String[] PATTERNS = new String[]{"request*", "order*", "save*"};

    @Bean
    public OrderRepositoryV1 orderRepositoryV1(LogTrace logTrace) {
        return (OrderRepositoryV1) Proxy.newProxyInstance(
                OrderRepositoryV1.class.getClassLoader(),
                new Class[]{OrderRepositoryV1.class},
                new LogTraceFilterHandler(new OrderRepositoryV1Impl(), logTrace, PATTERNS)
        );
    }

    @Bean
    public OrderServiceV1 orderServiceV1(LogTrace logTrace, OrderRepositoryV1 orderRepositoryV1) {
        return (OrderServiceV1) Proxy.newProxyInstance(
                OrderServiceV1.class.getClassLoader(),
                new Class[]{OrderServiceV1.class},
                new LogTraceFilterHandler(new OrderServiceV1Impl(orderRepositoryV1), logTrace, PATTERNS)
        );
    }

    @Bean
    public OrderControllerV1 orderControllerV1(LogTrace logTrace, OrderServiceV1 orderServiceV1) {
        return (OrderControllerV1) Proxy.newProxyInstance(
                OrderControllerV1.class.getClassLoader(),
                new Class[]{OrderControllerV1.class},
                new LogTraceFilterHandler(new OrderControllerV1Impl(orderServiceV1), logTrace, PATTERNS)
        );
    }
}
