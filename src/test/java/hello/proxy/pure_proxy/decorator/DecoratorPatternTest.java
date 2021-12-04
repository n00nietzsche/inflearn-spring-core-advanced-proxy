package hello.proxy.pure_proxy.decorator;

import hello.proxy.pure_proxy.decorator.code.Component;
import hello.proxy.pure_proxy.decorator.code.DecoratorPatternClient;
import hello.proxy.pure_proxy.decorator.code.RealComponent;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class DecoratorPatternTest {
    @Test
    void noDecorator() {
        Component realComponent = new RealComponent();
        DecoratorPatternClient decoratorPatternClient = new DecoratorPatternClient(realComponent);
        decoratorPatternClient.execute();
    }
}
