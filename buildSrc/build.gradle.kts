import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `kotlin-dsl`
    java
}

repositories {
    maven {
        url = uri("https://maven.aliyun.com/repository/public/")
    }
    gradlePluginPortal()
    mavenCentral()
}

dependencies {
    implementation(gradleApi())
    implementation("org.codehaus.groovy:groovy:3.0.22")
    implementation("cn.hutool:hutool-core:5.8.31")
    implementation("org.dom4j:dom4j:2.1.4")
    implementation("com.squareup:javapoet:1.13.0")
}

tasks.withType<KotlinCompile> {
    compilerOptions.jvmTarget.set(JvmTarget.JVM_1_8)
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(8))
    }
}