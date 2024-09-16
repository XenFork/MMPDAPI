pluginManagement {
    plugins {

    }
    repositories {
        mavenLocal()
        gradlePluginPortal()
        mavenCentral()
        maven("https://maven.fabricmc.net/")
        maven("https://repo.sleeping.town/")
        maven("https://maven.fabricmc.net/")
        maven("https://maven.neoforged.net/releases/")

    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version("0.8.0")

}



include("api")

project(":api").projectDir = file("modules/api")
val versions: String by settings


versions.split(",").forEach { version ->
    include("common-$version", "fabric-$version")
    project(":common-$version").projectDir = file("modules/interlayer/common/$version")
    project(":fabric-$version").projectDir = file("modules/interlayer/fabric/$version")
}

include("construct")
include("construct-processor")

project(":construct").projectDir = file("modules/construct")
project(":construct-processor").projectDir = file("modules/construct/processor")
//include("fabric")
//project(":fabric").projectDir = file("modules/fabric")