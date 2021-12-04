package hello.proxy.pure_proxy.decorator;

import hello.proxy.pure_proxy.decorator.code.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.sql.Time;

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

    @Test
    void decorator2() {
        Component realComponent = new RealComponent();
        Component messageDecorator = new MessageDecorator(realComponent);
        Component timeDecorator = new TimeDecorator(messageDecorator);

        DecoratorPatternClient decoratorPatternClient = new DecoratorPatternClient(timeDecorator);
        decoratorPatternClient.execute();
    }
}
