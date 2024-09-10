import java.nio.file.Files

plugins {
    `java-library`
    id("agency.highlysuspect.minivan") version("0.5")
    id("agency.highlysuspect.crossroad") version("0.3")
}

allprojects {
    apply(plugin = "java-library")
    apply(plugin = "agency.highlysuspect.minivan")
    apply(plugin = "agency.highlysuspect.crossroad")

    repositories {
        mavenLocal()
        mavenCentral()
        maven("https://repo.spongepowered.org/repository/maven-public/")
    }
}

subprojects {
    dependencies {
        compileOnly("org.projectlombok:lombok:1.18.34")
        annotationProcessor("org.projectlombok:lombok:1.18.34")
    }
}

val versions: String by project

versions.split(",").forEach {
    project(":common-${it}") {

        minivan {
            version(it)
        }
        val javas = this.file("src/main/java/io/github/xenfork/mmpd/V${it.replace("1.", "").replace(".", "_")}").toPath()
        val resources = this.file("src/main/resources").toPath()
        Files.createDirectories(javas)
        Files.createDirectories(resources)

        dependencies {
            implementation(project(":api"))
        }

    }
}