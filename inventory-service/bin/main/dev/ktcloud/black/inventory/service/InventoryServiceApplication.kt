package dev.ktcloud.black.inventory.service

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication
import org.springframework.transaction.annotation.EnableTransactionManagement

@EnableTransactionManagement
@ConfigurationPropertiesScan
@SpringBootApplication(scanBasePackages = ["dev.ktcloud.black"])
class InventoryServiceApplication


fun main(args: Array<String>) {
    runApplication<InventoryServiceApplication>(*args)
}