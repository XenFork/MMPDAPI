package io.github.xenfork.construct.annotation;

import io.github.xenfork.construct.annotation.enums.Environments;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.PACKAGE)
@Retention(RetentionPolicy.SOURCE)
public @interface MixinConfig {
    String pre() default  "";// pre file name
    String suf() default  "";// sub file name
    boolean required() default true;
    String minVersion() default "0.8";
    String compatibilityLevel() default "JAVA_8";
    Injectors injectors() default @Injectors;
    Environments environments() default Environments.all;
}
