import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val snippetsDir by extra { file("build/generated-snippets") }

plugins {
    val kotlinVersion = "1.8.22"

    id("org.springframework.boot") version "3.1.2"
    id("io.spring.dependency-management") version "1.1.2"
    id("org.asciidoctor.jvm.convert") version "3.3.2"

    kotlin("jvm") version kotlinVersion
    kotlin("plugin.spring") version kotlinVersion
    kotlin("plugin.jpa") version kotlinVersion
    kotlin("plugin.allopen") version kotlinVersion
    kotlin("kapt") version "1.6.10"
    id("org.jlleitschuh.gradle.ktlint") version "10.2.0"

    id("checkstyle")
    id("jacoco")
    idea
}

group = "com.synergy"
version = "1.0.0-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

allOpen {
    // Spring Boot 3.0.0
    annotation("jakarta.persistence.Entity")
    annotation("jakarta.persistence.MappedSuperclass")
    annotation("jakarta.persistence.Embeddable")
}

repositories {
    mavenCentral()
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

configurations.forEach {
    it.exclude(group = "org.springframework.boot", module = "spring-boot-starter-logging")
    it.exclude(group = "org.apache.logging.log4j", module = "log4j-to-slf4j")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "17"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

dependencies {
    // spring
    implementation("org.springframework.boot:spring-boot-starter-web")
    developmentOnly("org.springframework.boot:spring-boot-devtools")

    // kotlin
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")

    // AOP
    implementation("org.springframework.boot:spring-boot-starter-aop:3.1.0")

    // Log
    implementation("org.springframework.boot:spring-boot-starter-log4j2")

    // JPA
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")

    // Query Dsl
    implementation("com.querydsl:querydsl-jpa:5.0.0:jakarta")
    kapt("com.querydsl:querydsl-apt:5.0.0:jakarta")
    kapt("jakarta.annotation:jakarta.annotation-api")
    kapt("jakarta.persistence:jakarta.persistence-api")

    // WebFlux
    implementation("org.springframework.boot:spring-boot-starter-webflux:3.1.2")

    // database
    runtimeOnly("com.h2database:h2")
    implementation("com.mysql:mysql-connector-j:8.1.0")

    // Security
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-oauth2-client")
    implementation("com.google.api-client:google-api-client-jackson2:2.2.0")
    implementation("com.google.api-client:google-api-client:2.2.0")

    // JWT
    implementation("io.jsonwebtoken:jjwt-api:0.11.5")
    implementation("io.jsonwebtoken:jjwt-impl:0.11.5")
    implementation("io.jsonwebtoken:jjwt-jackson:0.11.5")

    // AWS S3
    implementation("org.springframework.cloud:spring-cloud-starter-aws:2.2.6.RELEASE")

    // test
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation(platform("org.junit:junit-bom:5.9.3"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("io.findify:s3mock_2.13:0.2.6")

    // spring rest docs
    testImplementation("org.springframework.restdocs:spring-restdocs-mockmvc")
}

/**
 * Spring Rest Docs
 */
tasks.asciidoctor {
    inputs.dir(snippetsDir)
    dependsOn(tasks.test)

    doFirst {
        delete {
            file("src/main/resources/static/docs")
        }
    }
}

tasks.register("copyHTML", Copy::class) {
    dependsOn(tasks.asciidoctor)
    from(file("build/docs/asciidoc/"))
    into(file("src/main/resources/static/docs"))
}

tasks.build {
    dependsOn(tasks.getByName("copyHTML"))
}

tasks.bootJar {
    dependsOn(tasks.asciidoctor)
    dependsOn(tasks.getByName("copyHTML"))
}

/**
 * Jacoco
 */
tasks.withType<JacocoReport> {
    reports {
        html.required.set(true)
        xml.required.set(false)
        csv.required.set(false)
    }

    finalizedBy("jacocoTestCoverageVerification")
}

tasks.withType<JacocoCoverageVerification> {
    violationRules {
        rule {
            enabled = true
            element = "CLASS"

            limit {
                counter = "LINE"
                value = "COVEREDRATIO"
                minimum = "0.00".toBigDecimal()
            }

            limit {
                counter = "LINE"
                value = "TOTALCOUNT"
                maximum = "200".toBigDecimal()
            }

            excludes = listOf(
                "com.freewill.ApplicationKt",
                "com.freewill.domain.**.entity.**",
                "com.freewill.domain.**.dto.**",
                "com.freewill.domain.**.controller.**",
                "com.freewill.config.**",
                "com.freewill.global.**",
                "com.freewill.security.**"
            )
        }
    }
}

val testCoverage by tasks.registering {
    group = "verification"
    description = "Runs the unit tests with coverage"

    dependsOn(
        ":test",
        ":jacocoTestReport",
        ":jacocoTestCoverageVerification",
    )

    tasks["jacocoTestReport"].mustRunAfter(tasks["test"])
    tasks["jacocoTestCoverageVerification"].mustRunAfter(tasks["jacocoTestReport"])
}

tasks.test {
    extensions.configure(JacocoTaskExtension::class) {
        destinationFile = file("$buildDir/jacoco/jacoco.exec")
    }
    outputs.dir(snippetsDir)
    finalizedBy("jacocoTestReport")
}

idea {
    module {
        val kaptMain = file("build/generated/source/kapt/main")
        sourceDirs.add(kaptMain)
        generatedSourceDirs.add(kaptMain)
    }
}
