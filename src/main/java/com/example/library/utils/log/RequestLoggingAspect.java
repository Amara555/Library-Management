package com.example.library.utils.log;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Arrays;


@Aspect
@Component
public class RequestLoggingAspect {
    private final HttpServletRequest request;
    private final ObjectMapper objectMapper;

    // Inject HttpServletRequest via constructor
    public RequestLoggingAspect(HttpServletRequest request,ObjectMapper objectMapper) {
        this.request = request;
        this.objectMapper = objectMapper;
    }
    private static final Logger logger = LoggerFactory.getLogger(RequestLoggingAspect.class);


    @Before("execution(* com.example.library.Controller..*(..))")
    public void logPostRequests(JoinPoint joinPoint) {
      try {
          MethodSignature signature = (MethodSignature) joinPoint.getSignature();
          Method method = signature.getMethod();

          // Check if method has @RequestMapping
          if (method.isAnnotationPresent(RequestMapping.class)) {
              RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);

              // Check if the method type contains POST
              if (Arrays.asList(requestMapping.method()).contains(RequestMethod.POST) || Arrays.asList(requestMapping.method()).contains(RequestMethod.PUT)) {
                  String requestUrl = request.getRequestURI(); // FIXED: Properly declared
                  String httpMethod = request.getMethod();
                  String queryParams = request.getQueryString();
                  String requestBody = extractRequestBody(joinPoint);
                  logger.info("📌 ADD Request: [Method: {}] [URL: {}] [Params: {}] [Body: {}]",
                          httpMethod, requestUrl, queryParams, requestBody);
              }
          }
      }catch (Exception e) {
          logger.error("❌ Error in RequestLoggingAspect: {}", e.getMessage(), e);
      }
    }
    // ✅ Declare the extractRequestBody method to prevent errors
    private String extractRequestBody(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        if (args.length > 0) {
            try {
                return objectMapper.writeValueAsString(args[0]); // Convert first argument to JSON
            } catch (Exception e) {
                return "❌ Failed to parse body: " + e.getMessage();
            }
        }
        return "No Body";
    }

}
