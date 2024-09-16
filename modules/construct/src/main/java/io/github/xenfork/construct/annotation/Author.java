package io.github.xenfork.construct.annotation;

public @interface Author {
    String name();
    Contact contact() default @Contact;
}
