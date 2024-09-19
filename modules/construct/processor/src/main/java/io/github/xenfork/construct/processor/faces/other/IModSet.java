package io.github.xenfork.construct.processor.faces.other;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import io.github.xenfork.construct.annotation.Depends;
import io.github.xenfork.construct.annotation.ModSet;

import java.util.Arrays;

public interface IModSet extends IAuthors, ILicense {
    default void fabricModSet(JSONObject json, ModSet modSet) {
        json
                .putOnce("schemaVersion", 1)
                .putOnce("id", modSet.modid())
                .putOnce("version", modSet.version())
                .putOnce("description", modSet.description())
                .putOnce("authors", authorSet(modSet.authors()))
                .putOnce("license", licenseSet(modSet.license()))
                .putOnce("icon", modSet.icon().replace("${modid}", modSet.modid()))
                .putOnce("environment", modSet.environment().getName());
        contactPut(modSet.contact(), json);
        notnullPutOnce(json, "name", modSet.modName());
        JSONObject depends = JSONUtil.createObj();
        JSONObject suggests = JSONUtil.createObj();
        JSONObject breaks = JSONUtil.createObj();
        JSONObject conflicts = JSONUtil.createObj();
        JSONObject recommends = JSONUtil.createObj();

        Arrays.stream(modSet.depends()).forEach(depend -> depends.putOnce(depend.modid(), depend.version()));
        Arrays.stream(modSet.suggests()).forEach(depend -> suggests.putOnce(depend.modid(), depend.version()));
        Arrays.stream(modSet.breaks()).forEach(depend -> breaks.putOnce(depend.modid(), depend.version()));
        Arrays.stream(modSet.conflicts()).forEach(depend -> conflicts.putOnce(depend.modid(), depend.version()));
        Arrays.stream(modSet.recommends()).forEach(depend -> recommends.putOnce(depend.modid(), depend.version()));

        notnullPutOnce(json, "depends", depends);
        notnullPutOnce(json, "suggests", suggests);
        notnullPutOnce(json, "breaks", breaks);
        notnullPutOnce(json, "conflicts", conflicts);
        notnullPutOnce(json, "recommends", recommends);


    }
}
