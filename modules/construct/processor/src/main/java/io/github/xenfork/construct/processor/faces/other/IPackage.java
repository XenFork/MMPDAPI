package io.github.xenfork.construct.processor.faces.other;

import javax.lang.model.element.*;

public interface IPackage {
    default String packageName(TypeElement element) {
        return String.valueOf(element.getQualifiedName()).replace("." + element.getSimpleName(), "");
    }

}
