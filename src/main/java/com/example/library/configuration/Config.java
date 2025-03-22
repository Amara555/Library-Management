package com.example.library.configuration;

import com.example.library.security.model.CustomUserDetails;
import com.example.library.utils.annotation.ParameterName;
import com.example.library.utils.model.HibernateAwareObjectMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import org.apache.commons.codec.binary.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.MethodParameter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

import java.lang.reflect.ParameterizedType;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


@Configuration
public class Config extends AcceptHeaderLocaleResolver implements WebMvcConfigurer {


    @Bean
    public MappingJackson2HttpMessageConverter customJackson2HttpMessageConverter() {
        MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter();
        ObjectMapper objectMapper = new HibernateAwareObjectMapper();
        jsonConverter.setObjectMapper(objectMapper);
        return jsonConverter;
    }



    @Bean
    public ApplicationContextHolder applicationContextHolder() {
        return new ApplicationContextHolder();
    }

    public static class ApplicationContextHolder implements ApplicationContextAware {
        private static ApplicationContext context;

        public static ApplicationContext getContext() {
            return context;
        }

        @Override
        public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
            context = applicationContext;
        }
    }

    public class ArgumentResolver implements HandlerMethodArgumentResolver {

        public boolean supportsParameter(MethodParameter methodParameter) {
            return true;
        }

        public Object resolveArgument(MethodParameter parameter,
                                      ModelAndViewContainer mavContainer,
                                      NativeWebRequest webRequest,
                                      WebDataBinderFactory binderFactory) throws Exception {
            ObjectMapper mapper = new ObjectMapper();

            Object paramValue = null;

            String requestValue = "";
            if (parameter.getParameterType().getSimpleName().equals(CustomUserDetails.class.getSimpleName())) {
                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                paramValue = authentication.getPrincipal();
                return paramValue;
            }

            if (parameter.hasParameterAnnotation(ParameterName.class)) {
                requestValue = webRequest.getParameter(
                        parameter.getParameterAnnotation(ParameterName.class).value());
                if (parameter.getParameterAnnotation(ParameterName.class).required() && requestValue == null) {
                    throw new RuntimeException("Parameter: " + parameter.getParameterAnnotation(ParameterName.class).value() +
                            " might be required");
                }


                if (requestValue != null && !"null".equalsIgnoreCase(requestValue)) {

                    requestValue = StringUtils.newStringUtf8(requestValue.getBytes());

                    if (parameter.getParameterType().getName().equals("java.util.List")) {
                        TypeFactory t = TypeFactory.defaultInstance();
                        paramValue = mapper.readValue(requestValue,
                                t.constructCollectionType(ArrayList.class, ((Class) ((ParameterizedType) parameter.getGenericParameterType()).getActualTypeArguments()[0])));

                    }
                    else if(parameter.getGenericParameterType() instanceof ParameterizedType){
                        TypeFactory t = TypeFactory.defaultInstance();
                        paramValue = mapper.readValue(requestValue,
                                t.constructParametricType(parameter.getParameterType(), ((Class) ((ParameterizedType) parameter.getGenericParameterType()).getActualTypeArguments()[0])));
                    }
                    else {
                        if ("Date".equals(parameter.getParameterType().getSimpleName())) {
                            String dateFormat = "yyyy-MM-dd";
                            if (parameter.hasParameterAnnotation(DateTimeFormat.class)) {
                                dateFormat = parameter.getParameterAnnotation(DateTimeFormat.class).pattern();
                            }
                            paramValue = new SimpleDateFormat(dateFormat).parse(requestValue);
                        } else {
                            paramValue = mapper.readValue(requestValue, parameter.getParameterType());
                        }
                    }
                }
            } else {

                paramValue = webRequest.getParameter(parameter.getParameterName());

            }

            return paramValue;
        }
    }
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(this.argumentResolver());
    }

    @Bean
    public ArgumentResolver argumentResolver() {
        return (new ArgumentResolver());
    }
}
