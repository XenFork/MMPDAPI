import cn.hutool.core.comparator.VersionComparator
import net.fabricmc.loom.LoomGradleExtension
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.nio.file.Files

plugins {
    `java-library`
    `maven-publish`
    id("agency.highlysuspect.minivan") version("0.5")
    id("agency.highlysuspect.crossroad") version("0.3")
    kotlin("jvm").version("2.0.20").apply(false)
    id("fabric-loom").version(loomVersion).apply(false)
}



allprojects {
    apply(plugin = "java-library")
    apply(plugin = "maven-publish")
    apply(plugin = "agency.highlysuspect.minivan")
    apply(plugin = "agency.highlysuspect.crossroad")

    base.archivesName = project.name
    group = "io.github.xenfork"
    version = "${project.name}-${modVersion}"

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
    val javaV = when {
        VersionComparator.INSTANCE.compare(it, "1.17") < 0 -> {
            8
        }
        VersionComparator.INSTANCE.compare(it, "1.18") < 0 -> {
            16
        }
        VersionComparator.INSTANCE.compare(it, "1.20.5") < 0 -> {
            17
        }
        else -> {
            21
        }
    }
    val javaT = if (javaV == 8) "JVM_1_8" else "JVM_$javaV"
    project(":common-${it}") {
        evaluationDependsOn(":api")
        minivan {
            version(it)
        }
        val javas = this.file("src/main/java/io/github/xenfork/mmpd/V${it.replace("1.", "").replace(".", "_")}").toPath()
        val resources = this.file("src/main/resources").toPath()
        Files.createDirectories(javas)
        Files.createDirectories(resources)

        java {
            withSourcesJar()
            sourceCompatibility = if (javaV == 8) JavaVersion.VERSION_1_8 else JavaVersion.toVersion(javaV)
            targetCompatibility = if (javaV == 8) JavaVersion.VERSION_1_8 else JavaVersion.toVersion(javaV)
            toolchain {
                languageVersion.set(JavaLanguageVersion.of(javaV))
            }
        }

        dependencies {
            implementation(project(":api"))
        }

    }
    project(":fabric-${it}") {
        apply(plugin = "fabric-loom")
        apply(plugin = "org.jetbrains.kotlin.jvm")
        evaluationDependsOn(":common-${it}")
        val javas = this.file("src/main/java/io/github/xenfork/mmpd/fabric/V${it.replace("1.", "").replace(".", "_")}").toPath()
        val resources = this.file("src/main/resources").toPath()
        Files.createDirectories(javas)
        Files.createDirectories(resources)

        tasks.withType<JavaCompile> {
            options.release.set(javaV)
        }
        tasks.withType<KotlinCompile> {
            compilerOptions.jvmTarget.set(JvmTarget.valueOf(javaT))
        }

        java {
            withSourcesJar()
            sourceCompatibility = if (javaV == 8) JavaVersion.VERSION_1_8 else JavaVersion.toVersion(javaV)
            targetCompatibility = if (javaV == 8) JavaVersion.VERSION_1_8 else JavaVersion.toVersion(javaV)
            toolchain {
                languageVersion.set(JavaLanguageVersion.of(javaV))
            }
        }

        dependencies {
            "minecraft"("com.mojang:minecraft:${it}")
            "mappings"(LoomGradleExtension.get(project).officialMojangMappings())
            "modImplementation"("net.fabricmc:fabric-language-kotlin:${kotlinLanguageVersion}")
        }
    }
}