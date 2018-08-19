package co.jware.uri.demo.spring;

import org.junit.Test;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.assertj.core.api.Assertions.assertThat;


public class ApiControllerAnnotationBeanPostProcessorTest {

    @Test
    public void annotationChange() {
        ApplicationContextRunner contextRunner = new ApplicationContextRunner()
                .withUserConfiguration(PostProcessorTestConfig.class);
        contextRunner.run(context -> {
            TestApiController apiController = context.getBean(TestApiController.class);
            assertThat(apiController).isNotNull();
            RequestMapping requestMapping = AnnotationUtils.findAnnotation(apiController.getClass(), RequestMapping.class);
            assertThat(requestMapping.path()[0]).isEqualTo("/api/there");
        });

    }
}