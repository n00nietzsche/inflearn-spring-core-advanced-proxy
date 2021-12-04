package hello.proxy.pure_proxy.decorator;

import hello.proxy.pure_proxy.decorator.code.Component;
import hello.proxy.pure_proxy.decorator.code.DecoratorPatternClient;
import hello.proxy.pure_proxy.decorator.code.MessageDecorator;
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

    @Test
    void decorator1() {
        Component realComponent = new RealComponent();
        Component messageDecorator = new MessageDecorator(realComponent);
        DecoratorPatternClient decoratorPatternClient = new DecoratorPatternClient(messageDecorator);

        decoratorPatternClient.execute();
    }
}
