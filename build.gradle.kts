val kotlinVersion = "1.2.70"

plugins {
    kotlin("jvm").version("1.2.70")
    `java-library`
    id("com.atlassian.performance.tools.gradle-release").version("0.4.3")
}

configurations.all {
    resolutionStrategy {
        activateDependencyLocking()
        failOnVersionConflict()
        eachDependency {
            when (requested.module.toString()) {
                "org.jetbrains:annotations" -> useVersion("13.0")
                "org.slf4j:slf4j-api" -> useVersion("1.7.25")
            }
            when (requested.group) {
                "org.jetbrains.kotlin" -> useVersion(kotlinVersion)
            }
        }
    }
}

dependencies {
    api("com.atlassian.performance.tools:ssh:[2.0.0,3.0.0)")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlinVersion")
    implementation("org.testcontainers:testcontainers:1.10.5")
    log4j(
        "api",
        "core",
        "slf4j-impl"
    ).forEach { implementation(it) }
    testCompile("junit:junit:4.12")
    testCompile("org.assertj:assertj-core:3.11.1")
}

fun log4j(
    vararg modules: String
): List<String> = modules.map { module ->
    "org.apache.logging.log4j:log4j-$module:2.10.0"
}

tasks.getByName("wrapper", Wrapper::class).apply {
    gradleVersion = "5.0"
    distributionType = Wrapper.DistributionType.ALL
}