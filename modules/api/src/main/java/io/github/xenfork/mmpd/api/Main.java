package io.github.xenfork.mmpd.api;

import io.github.xenfork.construct.annotation.Author;
import io.github.xenfork.construct.annotation.Contact;
import io.github.xenfork.construct.annotation.Init;
import io.github.xenfork.construct.annotation.ModSet;



@ModSet(
        modid = "mmpdapi",
        modName = "Multi-Mod Plugin Development Api",
        description = "This is Multi Mod Plugin Development Api",
        group = "io.github.xenfork.mmpd.api",
        authors = @Author(
                name = "baka4n",
                contact = @Contact(
                        homepage = "https://github.com/baka4n",
                        email = "474899581@qq.com",
                        issues = "https://github.com/baka4n/baka4n/issues"
                )
        ),
        version = "1.0.0-SNAPSHOT",
        contact = @Contact(
                homepage = "https://github.com/XenFork",
                issues = "https://github.com/XenFork/MMPDAPI/issues",
                sources = "https://github.com/XenFork/MMPDAPI"
        ),
        license = "MIT License"

)
public class Main {

    @Init()
    public static void init() {}

}
