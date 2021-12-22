package com.joke.annotation;

import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;

@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Target(ElementType.TYPE)
@Component
public @interface JokeConsumer {
    @AliasFor(annotation = Component.class)
    String value( ) default "";
}
