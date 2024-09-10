pluginManagement {
    repositories {
        mavenLocal()
        gradlePluginPortal()
        mavenCentral()
        maven("https://maven.fabricmc.net/")
        maven("https://repo.sleeping.town/")
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version("0.8.0")

}


include("api")
project(":api").projectDir = file("modules/api")
val versions: String by settings
versions.split(",").forEach { version ->
    include("common-$version")
    project(":common-$version").projectDir = file("modules/interlayer/common/$version")
}
