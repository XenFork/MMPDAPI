package io.github.xenfork.construct.processor.faces.find;

import cn.hutool.json.JSONObject;
import io.github.xenfork.construct.annotation.ModSet;
import io.github.xenfork.construct.processor.faces.other.IModSet;
import io.github.xenfork.construct.processor.faces.other.IPackage;
import io.github.xenfork.construct.processor.faces.other.IPoint;

import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;

import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

import static javax.lang.model.element.ElementKind.CLASS;

public interface IFindModSet extends IModSet, IPackage, IPoint {
    default ModSet findModSet(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv, JSONObject fabricModJson) {
        AtomicReference<ModSet> set = new AtomicReference<>();
        for (TypeElement annotation : annotations) {
            if (set.get() != null) break;
            for (Element element : roundEnv.getElementsAnnotatedWith(annotation)) {
                if (set.get() != null) break;
                if (element.getKind() == CLASS) {
                    TypeElement typeElement = (TypeElement) element;

                    notnullRunnable(typeElement.getAnnotation(ModSet.class), modSet -> {
                        set.set(modSet);
                        fabricModSet(fabricModJson, modSet);
                    });

                }
            }
        }
        return set.get();
    }
}
