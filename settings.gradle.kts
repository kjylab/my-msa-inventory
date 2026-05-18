pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)

    repositories {
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
        maven {
            url = uri("https://maven.pkg.github.com/kjylab/my-msa-common")
            credentials {
                username = System.getenv("GITHUB_ACTOR") ?: ""
                password = System.getenv("PACKAGES_TOKEN") ?: ""
            }
        }
    }
}

rootProject.name = "market"

include(
    "inventory",
    "inventory-event",
    "inventory-service",
)