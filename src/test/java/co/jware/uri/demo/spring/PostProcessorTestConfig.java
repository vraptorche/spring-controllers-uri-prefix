package co.jware.uri.demo.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PostProcessorTestConfig {
    @Bean
    public ApiControllerAnnotationBeanPostProcessor beanPostProcessor() {
        return new ApiControllerAnnotationBeanPostProcessor();
    }

    @Bean
    public TestApiController testApiController() {
        return new TestApiController();
    }
}
