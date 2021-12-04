package hello.proxy.pure_proxy.decorator.code;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MessageDecorator implements Component {

    private final Component component;

    public MessageDecorator(Component component) {
        this.component = component;
    }

    @Override
    public String operation() {
        log.info("MessageDecorator 실행");

        // 말 그대로 결과를 데코레이션 해준다. result -> *****result*****
        String result = component.operation();
        String decoResult = "*****" + result + "*****";

        log.info("MessageDecorator 꾸미기 적용 전={}, 적용 후={}", result, decoResult);

        return decoResult;
    }
}
