dependencies {
  implementation(project(":bank-domain"))
  implementation(project(":bank-monitoring"))
  implementation(project(":bank-core"))

  implementation("org.springframework.boot:spring-boot-starter")
  implementation("org.springframework.retry:spring-retry")
  implementation("org.springframework.boot:spring-boot-starter-data-jpa")
}
