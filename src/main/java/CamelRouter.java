import org.apache.camel.builder.RouteBuilder;

public class CamelRouter extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        from("direct:start")
                .circuitBreaker()
                .faultToleranceConfiguration().timeoutEnabled(true).timeoutDuration(2000).end()
                .log("Fault Tolerance processing start: ${threadName}")
                .to("http://fooservice.com/slow")
                .log("Fault Tolerance processing end: ${threadName}")
                .onFallback()
                .transform().constant("Fallback message")
                .endCircuitBreaker()
                .log("After Fault Tolerance ${body}");
    }
}