package io.github.xenfork.construct.annotation;

import io.github.xenfork.construct.annotation.enums.Points;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.SOURCE)
@Target({ElementType.METHOD,ElementType.TYPE})
public @interface EntryPoints {
    Points value() default Points.main;
    String target() default "";
    boolean kotlin() default false;




}
