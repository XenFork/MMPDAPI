package io.github.xenfork.construct.processor.faces.find;

import cn.hutool.json.JSONObject;
import io.github.xenfork.construct.annotation.EntryPoints;
import io.github.xenfork.construct.annotation.ModSet;
import io.github.xenfork.construct.processor.faces.other.IPoint;

import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;

import java.util.Set;

import static javax.lang.model.element.ElementKind.METHOD;

public interface IFindEntryPoints extends IPoint {
    default void findEntryPoints(ModSet modSet, Set<? extends TypeElement> annotations, RoundEnvironment roundEnv, JSONObject fabricEntryPoints) {
        for (TypeElement annotation : annotations) {
            for (Element element : roundEnv.getElementsAnnotatedWith(annotation)) {
                if (element.getKind() == METHOD) {
                    ExecutableElement executableElement = (ExecutableElement) element;
                    notnullRunnable(executableElement.getAnnotation(EntryPoints.class), entryPoints ->
                            put(entryPoints, executableElement, fabricEntryPoints));
                }
            }
        }
    }
}
