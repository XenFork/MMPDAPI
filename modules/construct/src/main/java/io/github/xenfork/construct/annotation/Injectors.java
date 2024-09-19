package io.github.xenfork.construct.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.SOURCE)
public @interface Injectors {
    int defaultRequire() default 1;
}
