package io.github.xenfork.construct.annotation.faces;

import io.github.xenfork.construct.annotation.ModSet;

import javax.lang.model.element.Element;

@FunctionalInterface
public interface EM {
    void apply(Element element, ModSet modSet);
}
