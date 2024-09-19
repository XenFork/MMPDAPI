package io.github.xenfork.construct.processor.faces.other;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import io.github.xenfork.construct.annotation.Author;

public interface IAuthors extends IContact {
    default JSONArray authorSet(Author[] authors) {
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
}
