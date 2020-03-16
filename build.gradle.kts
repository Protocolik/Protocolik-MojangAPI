plugins {
    kotlin("jvm") version "1.3.70"
    `maven-publish`
    maven
}

group = "com.github.protocolik"
version = "1.0.0"

repositories {
    mavenLocal()
    jcenter()
    maven { setUrl("https://jitpack.io/") }
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation("org.jetbrains.kotlinx", "kotlinx-coroutines-core", "1.3.4")
    api("it.unimi.dsi", "fastutil", "8.3.1")
    api("com.google.code.gson", "gson", "2.8.6")
    api("com.google.guava", "guava", "28.2-jre")
}