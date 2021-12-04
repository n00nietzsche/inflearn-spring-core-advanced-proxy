package hello.proxy.config.v1_proxy.interface_proxy;

import hello.proxy.app.v1_interface_and_class.OrderControllerV1;
import hello.proxy.trace.TraceStatus;
import hello.proxy.trace.logtrace.LogTrace;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class OrderControllerInterfaceProxy implements OrderControllerV1 {
    private final OrderControllerV1 orderControllerV1;
    private final LogTrace logTrace;

    @Override
    public String request(String itemId) {
        TraceStatus status = null;
        String result = null;

        try {
            status = logTrace.begin("OrderController.request()");
            result = orderControllerV1.request(itemId);
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
        return orderControllerV1.noLog();
    }
}
