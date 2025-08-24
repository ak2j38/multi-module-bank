dependencies {
  implementation(project(":bank-core"))
  implementation(project(":bank-domain"))
  implementation(project(":bank-event"))
  implementation(project(":bank-monitoring"))

  implementation("io.github.resilience4j:resilience4j-spring-boot3:2.0.2")
  implementation("io.github.resilience4j:resilience4j-circuitbreaker:2.0.2")
  implementation("io.github.resilience4j:resilience4j-retry:2.0.2")

  implementation("org.springframework.boot:spring-boot-starter-web")
  implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.3.0")
  implementation("org.springframework.boot:spring-boot-starter-data-jpa")
}
