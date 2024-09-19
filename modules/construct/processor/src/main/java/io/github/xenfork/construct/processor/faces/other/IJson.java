package io.github.xenfork.construct.processor.faces.other;

import cn.hutool.json.JSONObject;

public interface IJson {
    default void notnullPutOnce(JSONObject object, String k, String v) {
        if (!v.isEmpty()) {
            object.putOnce(k, v);
        }
    }

    default void notnullPutOnce(JSONObject object, String k, Object v) {
        object.putOnce(k, v);
    }
}
