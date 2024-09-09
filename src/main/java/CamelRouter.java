import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.FaultToleranceConfigurationDefinition;

public class CamelRouter extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        final var faultToleranceConfigurationDefinition = new FaultToleranceConfigurationDefinition();
        faultToleranceConfigurationDefinition.failureRatio(1);

        from("direct:start")
                .circuitBreaker()
                .faultToleranceConfiguration(faultToleranceConfigurationDefinition)
                .to("http://fooservice.com/slow")
                .onFallback()
                .transform().constant("Fallback message")
                .end()
                .to("mock:result");
    }
}