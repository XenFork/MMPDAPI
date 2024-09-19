package io.github.xenfork.construct.processor.faces.find;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import io.github.xenfork.construct.annotation.MixinConfig;
import io.github.xenfork.construct.annotation.ModSet;
import io.github.xenfork.construct.processor.faces.other.IUtil;

import javax.annotation.processing.Filer;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.tools.FileObject;
import javax.tools.StandardLocation;
import java.io.IOException;
import java.io.Writer;
import java.util.Set;

import static javax.lang.model.element.ElementKind.CLASS;
import static javax.lang.model.element.ElementKind.PACKAGE;

public interface IFindMixinConfig extends IUtil {

    default void findMixinConfig(ModSet modSet, Set<? extends TypeElement> annotations, RoundEnvironment roundEnv, Filer filer, JSONArray fabricMixins) {
        for (TypeElement annotation : annotations) {
            for (Element element : roundEnv.getElementsAnnotatedWith(annotation)) {
                if (element.getKind() == PACKAGE) {
                    PackageElement packageElement = (PackageElement) element;
                    MixinConfig mixinConfig = packageElement.getAnnotation(MixinConfig.class);
                    notnullRunnable(mixinConfig, config -> {
                        JSONObject obj = JSONUtil.createObj();
                        obj.putOnce("required", mixinConfig.required());
                        obj.putOnce("package", packageElement.getQualifiedName());
                        obj.putOnce("compatibilityLevel", mixinConfig.compatibilityLevel());
                        JSONArray mixins = JSONUtil.createArray();
                        for (Element enclosedElement : packageElement.getEnclosedElements()) {
                            if (enclosedElement.getKind() == CLASS) {
                                TypeElement typeElement = (TypeElement) enclosedElement;
                                mixins
                                        .add(typeElement
                                                .getQualifiedName()
                                                .toString()
                                                .replace(packageElement.getQualifiedName() + ".", ""));
                            }
                        }
                        obj.putOnce("mixins", mixins);
                        obj.putOnce("injectors", JSONUtil
                                .createObj()
                                .putOnce("defaultRequire", mixinConfig.injectors().defaultRequire()));
                        String mixinConfigPath = config.pre() + modSet.modid() + config.suf() + ".mixins.json";
                        switch (mixinConfig.environments()) {
                            case all:
                                fabricMixins.add(mixinConfigPath);
                                break;
                            default:
                                JSONObject object = JSONUtil.createObj();
                                object.putOnce("config", mixinConfigPath);
                                object.putOnce("environment", mixinConfig.environments().getName());
                                fabricMixins.add(object);
                                break;
                        }
                        try {

                            FileObject resource = filer.createResource(StandardLocation.SOURCE_OUTPUT,"", mixinConfigPath);
                            try(Writer writer = resource.openWriter()) {
                                writer.write(JSONUtil.toJsonPrettyStr(obj));
                            }
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    });
                }
            }
        }
    }
}
