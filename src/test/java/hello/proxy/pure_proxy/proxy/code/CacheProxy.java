package hello.proxy.pure_proxy.proxy.code;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CacheProxy implements Subject{
    private final Subject target;
    private String cacheValue;

    public CacheProxy(Subject target) {
        this.target = target;
    }

    @Override
    public String operation() {
        log.info("프록시 호출");
        // 처음 호출해서 값이 없다면, 캐싱은 불가능하기 때문에 일단은 값을 만들어야 한다.
        if(cacheValue == null) {
            cacheValue = target.operation();
        }

        return cacheValue;
    }
}
