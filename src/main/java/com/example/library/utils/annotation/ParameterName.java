package com.example.library.utils.annotation;

import java.lang.annotation.*;

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ParameterName {
    String value();

    boolean required() default true;
}
