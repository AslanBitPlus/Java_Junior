package org.example.less_2.hw;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface RndDate {

    long min() default 1704067200000L; // 1 января 2024 года UTC0
    long max() default 1735689600000L; // 1 января 2025 года UTC0

//  String s();
//
//  Gender gender();
//
//  Class<?> cl();
//
//  String[] ss();


    // все примитивы int, long, ...
    // String
    // любой enum
    // Class
    // массив всего, что выше

}
