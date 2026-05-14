plugins {
    kotlin("jvm")
    kotlin("plugin.jpa")
    kotlin("kapt")
}

dependencies {
    implementation("com.github.kjylab:my-msa-common:v1.0.6")
    implementation("com.github.kjylab:my-msa-client-redis:v1.+")
    implementation(project(":inventory-event"))

    kapt("com.querydsl:querydsl-apt:5.1.0:jakarta")
    kapt("jakarta.persistence:jakarta.persistence-api")
    kapt("jakarta.annotation:jakarta.annotation-api")

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.kafka:spring-kafka")
    runtimeOnly("com.h2database:h2")
}
