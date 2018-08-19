package co.jware.uri.demo;

import co.jware.uri.demo.spring.ApiControllerAnnotationBeanPostProcessor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class UriDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(UriDemoApplication.class, args);
    }

    @Bean
    public ApiControllerAnnotationBeanPostProcessor apiControllerAnnotationBeanPostProcessor() {
        return new ApiControllerAnnotationBeanPostProcessor();
    }
}
