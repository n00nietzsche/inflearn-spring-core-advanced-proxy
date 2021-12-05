package hello.proxy.config.v2_proxy.class_without_interface_proxy;

import hello.proxy.app.v2_class_without_interface.OrderRepositoryV2;
import hello.proxy.app.v2_class_without_interface.OrderServiceV2;
import hello.proxy.trace.TraceStatus;
import hello.proxy.trace.logtrace.LogTrace;

public class OrderServiceConcreteProxy extends OrderServiceV2 {
    private final OrderServiceV2 target;
    private final LogTrace logTrace;

    public OrderServiceConcreteProxy(OrderServiceV2 target, LogTrace logTrace) {
        /**
         * 자바 문법상, 상속을 받으면 당연히 상위 클래스의 기능을 이용할 수 있다는 가정이 있기 때문에
         * 부모 클래스의 기능을 정상적으로 이용하기 위해 부모 생성자인 super() 메소드를 호출해야 한다.
         * 그러나, 우리는 부모 클래스의 기능을 이용하지 않을 것이기 때문에 null 을 넣어준다.
         *
         * 사실 null 을 넣지 않고, 제대로 부모 생성자에 대한 인수를 준 다음 super 의 내부 메서드를 사용해도 되지만
         * 이번엔 프록시 개념을 이용하는 학습을 하는 것이 목적이므로, 부모 클래스의 기능을 이용하지 않는다.
         */
        super(null);
        this.logTrace = logTrace;
        this.target = target;
    }

    @Override
    public void orderItem(String itemId) {
        TraceStatus status = null;

        try{
            status = logTrace.begin("OrderService.orderItem()");
            target.orderItem(itemId);
            logTrace.end(status);
        }catch(Exception e) {
            logTrace.exception(status, e);
            e.printStackTrace();
        }
    }
}
