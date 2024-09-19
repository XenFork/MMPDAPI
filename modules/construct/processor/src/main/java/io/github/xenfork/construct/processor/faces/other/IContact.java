package io.github.xenfork.construct.processor.faces.other;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import io.github.xenfork.construct.annotation.Contact;

public interface IContact extends IJson {
    default void contactPut(Contact contact, JSONObject parent) {
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
}
