package io.github.xenfork.mmpd.api.resources;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class IResourceLocation {
    private String modid;
    private String path;
}
