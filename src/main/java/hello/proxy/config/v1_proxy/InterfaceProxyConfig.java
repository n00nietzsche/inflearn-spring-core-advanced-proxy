package hello.proxy.config.v1_proxy;

import hello.proxy.app.v1_interface_and_class.*;
import hello.proxy.config.v1_proxy.interface_proxy.OrderControllerInterfaceProxy;
import hello.proxy.config.v1_proxy.interface_proxy.OrderRepositoryInterfaceProxy;
import hello.proxy.config.v1_proxy.interface_proxy.OrderServiceInterfaceProxy;
import hello.proxy.trace.logtrace.LogTrace;
import hello.proxy.trace.logtrace.ThreadLocalLogTrace;
import lombok.extern.java.Log;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InterfaceProxyConfig {

    @Bean
    // 빈 선언에 파라미터에 타입을 넣어두면 자동으로 타입에 맞는 빈이 주입된다.
    public OrderControllerV1 orderController(LogTrace logTrace, OrderServiceV1 orderServiceV1) {
        OrderControllerV1Impl orderControllerV1Impl = new OrderControllerV1Impl(orderServiceV1);
        return new OrderControllerInterfaceProxy(orderControllerV1Impl, logTrace);
    }

    @Bean
    public OrderServiceV1 orderService(LogTrace logTrace, OrderRepositoryV1 orderRepositoryV1) {
        OrderServiceV1Impl orderServiceV1Impl = new OrderServiceV1Impl(orderRepositoryV1);
        return new OrderServiceInterfaceProxy(orderServiceV1Impl, logTrace);
    }

    @Bean
    public OrderRepositoryV1 orderRepository(LogTrace logTrace) {
        OrderRepositoryV1Impl orderRepositoryV1Impl = new OrderRepositoryV1Impl();
        return new OrderRepositoryInterfaceProxy(orderRepositoryV1Impl, logTrace);
    }
}
