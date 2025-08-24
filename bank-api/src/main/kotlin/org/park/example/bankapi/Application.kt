package org.park.example.bankapi

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication(scanBasePackages = ["org.park.example"])
class Application

fun main(args: Array<String>) {
  runApplication<Application>(*args)
}
