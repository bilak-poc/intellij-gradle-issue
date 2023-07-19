plugins {
    java
    idea
    id("org.springframework.boot") version "3.1.1"
    id("io.spring.dependency-management") version "1.1.0"
    id("org.openapi.generator") version "6.6.0"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"

val mapstructVersion = "1.5.5.Final"
val swaggerAnnotationsVersion = "2.2.14"

val openapiBasePackage = "com.example.demo.generated"

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

sourceSets {
    main {
        java {
            srcDirs.add(file("${buildDir}/generated-sources/src/main/java"))
        }
    }
}

idea {
    module.generatedSourceDirs.add(file("${buildDir}/generated/sources/annotationProcessor/java"))
    module.generatedSourceDirs.add(file("${buildDir}/generated-sources/src/main/java"))
    println("DBGDBG ${module.sourceDirs}")
    println("DBGDBG ${module.generatedSourceDirs}")
}

repositories {
    mavenCentral()
}

dependencies {
    annotationProcessor("org.hibernate:hibernate-jpamodelgen:6.2.6.Final")
    annotationProcessor("org.mapstruct:mapstruct-processor:$mapstructVersion")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.mapstruct:mapstruct:$mapstructVersion")
    implementation("io.swagger.core.v3:swagger-annotations:$swaggerAnnotationsVersion")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

openApiGenerate {
    apiPackage.set("$openapiBasePackage.api")
    cleanupOutput.set(true)
    configOptions.set(mapOf(
            "hideGenerationTimestamp" to "true",
            "interfaceOnly" to "true",
            "openApiNullable" to "false",
            "requestMappingMode" to "api_interface",
            "useTags" to "true"
    ))
    generateApiTests.set(false)
    generatorName.set("spring")
    inputSpec.set("$projectDir/src/main/resources/openapi/openapi.yaml")
    invokerPackage.set("$openapiBasePackage.invoker")
    outputDir.set("$buildDir/generated-sources")
    modelPackage.set("$openapiBasePackage.model")
    modelNameSuffix.set("DTO")
}

tasks.compileJava {
    dependsOn("openApiGenerate")
    options.encoding = "UTF-8"
}

tasks.withType<Test> {
    useJUnitPlatform()
}
