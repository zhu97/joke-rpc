package com.joke.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Target(ElementType.TYPE)
@Component
public @interface JokeService {
    Class<?> value();

    String version() default "";
}
