package co.jware.uri.demo.spring;

import co.jware.uri.demo.annotation.ApiController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;

@Slf4j
public class ApiControllerAnnotationBeanPostProcessor implements BeanPostProcessor, EnvironmentAware {

    private static final String ANNOTATION_METHOD = "annotationData";
    private static final String DECLARED_ANNOTATIONS = "declaredAnnotations";
    private Environment env;

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        ApiController annotation = AnnotationUtils.findAnnotation(bean.getClass(), ApiController.class);
        if (null != annotation) {
            RequestMapping requestMapping = AnnotationUtils.findAnnotation(bean.getClass(), RequestMapping.class);
            if (null != requestMapping) {
                Map<String, Object> attributes = AnnotationUtils.getAnnotationAttributes(requestMapping);
                String apiPrefix = env.getProperty("api.prefix", "/api");
                String[] values = (String[]) attributes.get("path");
                values = Arrays.stream(values)
                        .filter(s -> !s.startsWith(apiPrefix))
                        .map(s -> apiPrefix + s).toArray(String[]::new);
                if (values.length > 0) {
                    attributes.put("value", values);
                    attributes.put("path", values);
                    RequestMapping target = AnnotationUtils.synthesizeAnnotation(attributes,
                            RequestMapping.class, null);
                    changeAnnotationValue(bean.getClass(), RequestMapping.class, target);
//                    changeAnnotationValueMethodHandles(bean.getClass(), RequestMapping.class, target);
                }
            }
        }
        return bean;
    }

    @SuppressWarnings("unchecked")
    private static void changeAnnotationValue(Class<?> targetClass, Class<? extends Annotation> targetAnnotation, Annotation targetValue) {
        try {
            Method method = Class.class.getDeclaredMethod(ANNOTATION_METHOD);
            method.setAccessible(true);
            Object annotationData = method.invoke(targetClass);
            Field annotations = annotationData.getClass().getDeclaredField(DECLARED_ANNOTATIONS);
            annotations.setAccessible(true);
            Map<Class<? extends Annotation>, Annotation> map = (Map<Class<? extends Annotation>, Annotation>) annotations.get(annotationData);
            map.put(targetAnnotation, targetValue);
        } catch (Exception e) {
            log.error("Error changing annotation", e);
        }
    }

    @Override
    public void setEnvironment(Environment environment) {
        env = environment;
    }
}
