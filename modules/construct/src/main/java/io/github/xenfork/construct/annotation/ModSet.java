package io.github.xenfork.construct.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.TYPE)
public @interface ModSet {
    String minecraftVersions() default "*";// [1.14,) or more
    String group(); // a.b.c.d.e
    String modid();
    String modName() default "";// examplemod
    String description() default "";
    Author[] authors();
    String version();
    Contact contact() default @Contact;
    String[] license() default {};
    String icon() default "assets/${modid}/icon.png";
    Environments environment() default Environments.all;
}
