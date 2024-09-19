package io.github.xenfork.construct.processor.faces.other;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;

import java.util.function.Consumer;

public interface IUtil {
    default <T> T notnullRunnable(T t, Consumer<T> consumer) {
       if (t != null)
           consumer.accept(t);
       return t;

    }

    default void notnullArrayRunnable(JSONObject jsonObject, String key, Consumer<JSONArray> consumer) {
        JSONArray array = jsonObject.isNull(key) ? JSONUtil.createArray() : jsonObject.getJSONArray(key);
        consumer.accept(array);
        jsonObject.putOpt(key, array);
    }

    default void notnullObjectRunnable(JSONObject jsonObject, String key, Consumer<JSONObject> consumer) {
        JSONObject array = jsonObject.isNull(key) ? JSONUtil.createObj() : jsonObject.getJSONObject(key);
        consumer.accept(array);
        jsonObject.putOpt(key, array);
    }
}
