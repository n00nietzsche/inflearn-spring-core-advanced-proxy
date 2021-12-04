package hello.proxy.pure_proxy.proxy;

import hello.proxy.pure_proxy.proxy.code.CacheProxy;
import hello.proxy.pure_proxy.proxy.code.ProxyPatternClient;
import hello.proxy.pure_proxy.proxy.code.RealSubject;
import org.junit.jupiter.api.Test;

public class ProxyPatternTest {
    @Test
    void noProxyTest() {
        RealSubject realSubject = new RealSubject();
        ProxyPatternClient proxyPatternClient = new ProxyPatternClient(realSubject);

        proxyPatternClient.execute();
        proxyPatternClient.execute();
        proxyPatternClient.execute();
    }

    @Test
    void cacheProxyTest() {
        // 캐싱이 안됐다면, proxyPatternClient -> cacheProxy -> realSubject
        // 캐싱이 됐다면, proxyPatternClient -> cacheProxy
        RealSubject realSubject = new RealSubject();
        CacheProxy cacheProxy = new CacheProxy(realSubject);
        ProxyPatternClient proxyPatternClient = new ProxyPatternClient(cacheProxy);

        proxyPatternClient.execute();
        proxyPatternClient.execute();
        proxyPatternClient.execute();
    }
}
