package io.github.xenfork.construct.processor;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.google.auto.service.AutoService;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;
import io.github.xenfork.construct.annotation.ModSet;
import io.github.xenfork.construct.processor.faces.ISave;
import io.github.xenfork.construct.processor.faces.find.IFindEntryPoints;
import io.github.xenfork.construct.processor.faces.find.IFindMixinConfig;
import io.github.xenfork.construct.processor.faces.find.IFindModSet;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.*;
import javax.tools.FileObject;
import javax.tools.StandardLocation;
import java.io.IOException;
import java.io.Writer;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

@SupportedAnnotationTypes({
        "io.github.xenfork.construct.annotation.ModSet",
        "io.github.xenfork.construct.annotation.EntryPoints",
        "io.github.xenfork.construct.annotation.MixinConfig",
})
@AutoService(Processor.class)
public class ModSetProcessor extends AbstractProcessor implements IFindModSet, IFindEntryPoints, IFindMixinConfig, ISave {
    private Filer filer;

    public static final AtomicBoolean b = new AtomicBoolean(true);

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        filer = processingEnv.getFiler();

    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        String property = System.getProperty("java.specification.version");
        return SourceVersion.valueOf("RELEASE_" + (property.equals("1.8") ? 8 : property) );

    }

    public static final JSONObject fabricModJson = JSONUtil.createObj();
    public static final JSONObject fabricEntryPoints = JSONUtil.createObj();
    public static final JSONArray mixins = JSONUtil.createArray();
    public static final ConcurrentHashMap<String, JSONObject> mixinConfigs = new ConcurrentHashMap<>();

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        ModSet modSet = findModSet(annotations, roundEnv, fabricModJson);
        if (modSet != null) {
            findEntryPoints(modSet, annotations, roundEnv, fabricEntryPoints);
            findMixinConfig(modSet, annotations, roundEnv, filer, mixins);
        }
        if (b.get()) {
            save(fabricModJson, fabricEntryPoints, mixins, filer);
            b.set(false);
        }
        return true;
    }

    private void build(TypeSpec.Builder builder, AtomicReference<ModSet> reference) {
        ModSet modSet = reference.get();
        if (builder != null && modSet != null) {
            JavaFile javaFile = JavaFile.builder(modSet.group(), builder.build()).build();
            try {
                javaFile.writeTo(filer);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
