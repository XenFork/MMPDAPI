package io.github.xenfork.construct.processor.faces.other;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import java.util.Optional;

public interface IClass {
    default Optional<TypeElement> getClass(ExecutableElement element) {
        Element enclosingElement = element.getEnclosingElement();
        if (enclosingElement.getKind() == ElementKind.CLASS) {
            return Optional.of((TypeElement) enclosingElement);
        }
        return Optional.empty();
    }
}
