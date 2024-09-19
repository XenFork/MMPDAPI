package io.github.xenfork.construct.processor.faces;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;

import javax.annotation.processing.Filer;
import javax.tools.FileObject;
import javax.tools.StandardLocation;
import java.io.IOException;
import java.io.Writer;

public interface ISave {
    default void save(JSONObject fabricModJson, JSONObject fabricEntryPoints, JSONArray mixins, Filer filer) {
        if (fabricModJson.isNull("entrypoints"))
            fabricModJson.putOnce("entrypoints", fabricEntryPoints);
        if (fabricModJson.isNull("mixins"))
            fabricModJson.putOnce("mixins", mixins);
        try {
            FileObject resource = filer.createResource(StandardLocation.SOURCE_OUTPUT,"", "fabric.mods.json");
            try (Writer writer = resource.openWriter()) {
                writer.write(JSONUtil.toJsonPrettyStr(fabricModJson));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
