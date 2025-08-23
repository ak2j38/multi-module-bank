dependencies {
  implementation(project(":bank-core"))
  implementation(project(":bank-domain"))
  implementation(project(":bank-event"))

  implementation("org.springframework.boot:spring-boot-starter-web")
  // swagger
  implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.3.0")
}
