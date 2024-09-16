package io.github.xenfork.construct.processor;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.google.auto.service.AutoService;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;
import io.github.xenfork.construct.annotation.Author;
import io.github.xenfork.construct.annotation.Contact;
import io.github.xenfork.construct.annotation.ModSet;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.*;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@SupportedAnnotationTypes({
        "io.github.xenfork.construct.annotation.ModSet",
        "io.github.xenfork.construct.annotation.Init"
})
@AutoService(Processor.class)
public class ModSetProcessor extends AbstractProcessor {
    private Filer filer;

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

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {

        for (TypeElement annotation : annotations) {
            for (TypeElement typeElement : roundEnv
                    .getElementsAnnotatedWith(annotation).stream()
                    .filter(element -> element.getKind().isClass())
                    .map(element -> (TypeElement) element).collect(Collectors.toSet())) {
                ModSet modSet = typeElement.getAnnotation(ModSet.class);
                if (modSet != null) {
                    JSONObject fabricModJson =
                            JSONUtil.createObj()
                                    .putOnce("schemaVersion", 1)
                                    .putOnce("id", modSet.modid())
                                    .putOnce("version", modSet.version())
                                    .putOnce("description", modSet.description())
                                    .putOnce("authors", authorSet(modSet.authors()))
                                    .putOnce("license", licenseSet(modSet.license()));
                    contactPut(modSet.contact(), fabricModJson);
                    notnullPutOnce( fabricModJson, "name", modSet.modName());



                    System.out.println(JSONUtil.toJsonPrettyStr(fabricModJson));

                }
            }
        }
        return true;
    }

    private Object licenseSet(String[] licenses) {
        switch (licenses.length) {
            case 1:
                return licenses[0];
            case 0:
                return null;
            default:
                JSONArray array = JSONUtil.createArray();
                array.addAll(Arrays.asList(licenses));
                return array;
        }
    }

    private JSONArray authorSet(Author[] authors) {
        JSONArray array = JSONUtil.createArray();
        for (Author author : authors) {
            if (
                    author.contact().homepage().isEmpty() &&
                    author.contact().email().isEmpty() &&
                    author.contact().irc().isEmpty() &&
                    author.contact().sources().isEmpty() &&
                    author.contact().issues().isEmpty()
            ) {
                array.add(author.name());
            } else {
                JSONObject entries = JSONUtil.createObj();
                entries.putOnce("name", author.name());
                contactPut(author.contact(), entries);
                array.add(entries);
            }

        }
        return array;
    }

    private void contactPut(Contact contact, JSONObject parent) {
        JSONObject obj = JSONUtil.createObj();
        notnullPutOnce(obj,"email",contact.email());
        notnullPutOnce(obj,"homepage",contact.homepage());
        notnullPutOnce(obj,"sources",contact.sources());
        notnullPutOnce(obj,"irc",contact.irc());
        notnullPutOnce(obj,"issues",contact.issues());
        if (!obj.isEmpty()) {
            parent.putOnce("contact", obj);
        }
    }

    public static void notnullPutOnce(JSONObject object, String k, String v) {
        if (!v.isEmpty()) {
            object.putOnce(k, v);
        }
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
