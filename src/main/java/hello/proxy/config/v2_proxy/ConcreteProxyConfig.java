package hello.proxy.config.v2_proxy;

import hello.proxy.app.v2_class_without_interface.OrderControllerV2;
import hello.proxy.app.v2_class_without_interface.OrderRepositoryV2;
import hello.proxy.app.v2_class_without_interface.OrderServiceV2;
import hello.proxy.config.v2_proxy.class_without_interface_proxy.OrderControllerConcreteProxy;
import hello.proxy.config.v2_proxy.class_without_interface_proxy.OrderRepositoryConcreteProxy;
import hello.proxy.config.v2_proxy.class_without_interface_proxy.OrderServiceConcreteProxy;
import hello.proxy.trace.logtrace.LogTrace;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConcreteProxyConfig {
    @Bean
    public OrderControllerV2 orderControllerV2(OrderServiceV2 orderServiceV2, LogTrace logTrace) {
        return new OrderControllerConcreteProxy(new OrderControllerV2(orderServiceV2), logTrace);
    }

    @Bean
    public OrderServiceV2 orderServiceV2(OrderRepositoryV2 orderRepositoryV2, LogTrace logTrace) {
        return new OrderServiceConcreteProxy(new OrderServiceV2(orderRepositoryV2), logTrace);
    }

    @Bean
    public OrderRepositoryV2 orderRepositoryV2(LogTrace logTrace) {
        return new OrderRepositoryConcreteProxy(new OrderRepositoryV2(), logTrace);
    }
}
