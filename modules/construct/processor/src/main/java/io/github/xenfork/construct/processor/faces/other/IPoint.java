package io.github.xenfork.construct.processor.faces.other;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import io.github.xenfork.construct.annotation.EntryPoints;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Name;
import javax.lang.model.element.TypeElement;
import java.util.Optional;

public interface IPoint extends IUtil, IClass {
    default void put(EntryPoints entryPoints, ExecutableElement executableElement, JSONObject fabricEntryPoints) {
        Name simpleName = executableElement.getSimpleName();
        Optional<TypeElement> optionalTypeElement = getClass(executableElement);
        optionalTypeElement.ifPresent(typeElement -> {
            Name qualifiedName = typeElement.getQualifiedName();
            String valueInject = qualifiedName + "::" + simpleName;
            String name = entryPoints.target().isEmpty() ? entryPoints.value().name() : entryPoints.target();

            if (!fabricEntryPoints.containsKey(name)) {
                fabricEntryPoints.putOnce(name, JSONUtil.createArray());
            }
            JSONArray array = fabricEntryPoints.getJSONArray(name);
            array.add(entryPoints.kotlin()
                    ? JSONUtil.createObj().putOnce("adapter", "kotlin").putOnce("value", valueInject)
                    : valueInject);

        });
    }
}
