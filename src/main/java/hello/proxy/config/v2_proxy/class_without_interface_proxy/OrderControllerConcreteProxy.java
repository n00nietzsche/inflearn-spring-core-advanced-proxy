package hello.proxy.config.v2_proxy.class_without_interface_proxy;

import hello.proxy.app.v2_class_without_interface.OrderControllerV2;
import hello.proxy.trace.TraceStatus;
import hello.proxy.trace.logtrace.LogTrace;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class OrderControllerConcreteProxy extends OrderControllerV2 {
    private final OrderControllerV2 target;
    private final LogTrace logTrace;

    public OrderControllerConcreteProxy(OrderControllerV2 target, LogTrace logTrace) {
        super(null);
        this.target = target;
        this.logTrace = logTrace;
    }

    @Override
    public String request(String itemId) {
        TraceStatus status = null;
        String result = null;

        try {
            status = logTrace.begin("OrderController.request()");
            result = target.request(itemId);
            logTrace.end(status);

            return result;
        } catch (Exception e) {
            logTrace.exception(status, e);
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public String noLog() {
        return super.noLog();
    }
}
