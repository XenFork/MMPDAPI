package io.github.xenfork.construct.annotation;

public @interface Contact {
    String homepage() default "";
    String email() default "";
    String sources() default "";
    String irc() default "";
    String issues() default "";
}
