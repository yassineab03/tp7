package ma.ws.jaxrs.config;
import ma.ws.jaxrs.controllers.CompteRestJaxRSAPI;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.springframework.context.annotation.*;
@Configuration
public class MyConfig {
 @Bean public ResourceConfig resourceConfig() {
  return new ResourceConfig().register(CompteRestJaxRSAPI.class).register(JacksonFeature.class);
 }
}
