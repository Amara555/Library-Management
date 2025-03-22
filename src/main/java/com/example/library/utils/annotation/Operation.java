package com.example.library.utils.annotation;

import com.example.library.utils.services.OperationActions;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Operation {
    OperationActions type();//checkin or checkout
}
