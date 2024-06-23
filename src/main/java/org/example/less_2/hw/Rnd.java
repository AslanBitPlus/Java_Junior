package org.example.less_2.hw;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Rnd {

    int min() default 0;
    int max() default 100;

    // все примитивы int, long, ...
    // String
    // любой enum
    // Class
    // массив всего, что выше

}

