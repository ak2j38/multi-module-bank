plugins {
  kotlin("jvm") version "1.9.25" apply false
  kotlin("plugin.spring") version "1.9.25" apply false
  kotlin("plugin.jpa") version "1.9.25" apply false
  id("org.springframework.boot") version "3.5.4" apply false
  id("io.spring.dependency-management") version "1.1.7" apply false
}

allprojects {
  group = "org.park.example"
  version = "0.0.1-SNAPSHOT"
  description = "multi-module-bank"
  repositories {
    mavenCentral()
  }
}

subprojects {
  apply(plugin = "org.jetbrains.kotlin.jvm")
  apply(plugin = "io.spring.dependency-management")

  if (name == "bank-api") {
    apply(plugin = "org.springframework.boot")
    apply(plugin = "org.jetbrains.kotlin.plugin.spring")
    apply(plugin = "org.jetbrains.kotlin.plugin.jpa")
  }

  if (name == "bank-core") {
    apply(plugin = "org.jetbrains.kotlin.plugin.spring")
  }

  if (name == "bank-domain") {
    apply(plugin = "org.jetbrains.kotlin.plugin.jpa")
  }

  tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
      jvmTarget = "17"
      freeCompilerArgs += "-Xjsr305=strict"
    }
  }

  tasks.withType<Test> {
    useJUnitPlatform()
  }
}
