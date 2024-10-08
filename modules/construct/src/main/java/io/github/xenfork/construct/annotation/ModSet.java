package io.github.xenfork.construct.annotation;

import io.github.xenfork.construct.annotation.enums.Environments;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.TYPE)
public @interface ModSet {
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
    Depends[] depends() default {
            @Depends(modid = "java", version = ">=8"),
            @Depends(modid = "minecraft", version = ">=1.14"),
            @Depends(modid = "fabric", version = "*"),
            @Depends(modid = "fabric-language-kotlin", version = "*")
    };
    Depends[] suggests() default {
        @Depends(modid = "another-mod", version = "*")
    };
    Depends[] breaks() default {};
    Depends[] conflicts() default {};
    Depends[] recommends() default {};
}
