package io.github.xenfork.construct.processor.faces.other;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;

import java.util.Arrays;

public interface ILicense {
    default Object licenseSet(String[] licenses) {
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
}
