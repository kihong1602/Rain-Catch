package oo.kr.shared.global.log;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.CodeSignature;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Aspect
@Component
public class ControllerLoggingAspect {

  private final ObjectWriter objectWriter;

  public ControllerLoggingAspect(ObjectMapper objectMapper) {
    this.objectWriter = objectMapper.writerWithDefaultPrettyPrinter();
  }

  @Pointcut("within(oo.kr.shared.domain..*.controller..*)")
  public void controllerPointCut() {
  }

  @Around("controllerPointCut()")
  public Object loggingRequestInfo(ProceedingJoinPoint joinPoint) throws Throwable {
    Class<?> clazz = joinPoint.getTarget()
                              .getClass();
    Object result = null;

    try {
      result = joinPoint.proceed(joinPoint.getArgs());
      return result;
    } finally {
      log.info(getRequestUrl(joinPoint, clazz));
      log.info("parameters {}", objectWriter.writeValueAsString(params(joinPoint)));
      log.info("response: {}", objectWriter.writeValueAsString(result));
    }

  }

  private String getRequestUrl(JoinPoint joinPoint, Class<?> clazz) {
    MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
    Method method = methodSignature.getMethod();
    RequestMapping requestMapping = clazz.getAnnotation(RequestMapping.class);
    String baseUrl = requestMapping.value()[0];
    String url = Stream.of(GetMapping.class, PostMapping.class, PutMapping.class, PatchMapping.class,
                           DeleteMapping.class,
                           RequestMapping.class)
                       .filter(method::isAnnotationPresent)
                       .map(mappingClass -> getUrl(method, mappingClass, baseUrl))
                       .findFirst()
                       .orElse(null);
    return url;
  }

  private String getUrl(Method method, Class<? extends Annotation> annotationClass, String baseUrl) {
    Annotation annotation = method.getAnnotation(annotationClass);
    String[] values;
    String httpMethod;
    try {
      values = (String[]) annotationClass.getMethod("value")
                                         .invoke(annotation);
      httpMethod = annotationClass.getSimpleName()
                                  .replace("Mapping", "")
                                  .toUpperCase();
    } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
      return "로깅 실패";
    }
    return """
        %s %s%s""".formatted(httpMethod, baseUrl, values.length > 0 ? values[0] : "");
  }


  private Map<String, Object> params(JoinPoint joinPoint) {
    CodeSignature codeSignature = (CodeSignature) joinPoint.getSignature();
    String[] parameterNames = codeSignature.getParameterNames();
    Object[] args = joinPoint.getArgs();
    Map<String, Object> params = new HashMap<>();
    for (int i = 0; i < parameterNames.length; i++) {
      params.put(parameterNames[i], args[i]);
    }
    return params;
  }
}
