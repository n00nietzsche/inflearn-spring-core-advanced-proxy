package hello.proxy.pure_proxy.concrete_proxy;

import hello.proxy.pure_proxy.concrete_proxy.code.ConcreteClient;
import hello.proxy.pure_proxy.concrete_proxy.code.ConcreteLogic;
import hello.proxy.pure_proxy.concrete_proxy.code.TimeProxy;
import org.junit.jupiter.api.Test;

public class ConcreteProxyTest {

    @Test
    void noProxy() {
        ConcreteLogic concreteLogic = new ConcreteLogic();
        ConcreteClient client = new ConcreteClient(concreteLogic);
        client.execute();
    }

    @Test
    void addProxy() {
        ConcreteLogic concreteLogic = new ConcreteLogic();
        TimeProxy timeProxy = new TimeProxy(concreteLogic);
        ConcreteClient concreteClient = new ConcreteClient(timeProxy);

        concreteClient.execute();
    }
}
