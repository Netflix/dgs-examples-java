/*
 * Copyright 2021 Netflix, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


plugins {
    id("java")
    id("com.netflix.dgs.codegen") version "4.6.7"
    id("org.springframework.boot") version "2.4.6"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_1_8

// If you use Spring Boot Gradle Plugin 2.3.+ you will have to explicitly set the Kotlin Version to 1.4.+.
// The plugin will downgrade Kotlin to its 1.3.x version, which is not compatible.
// You do this by setting the version into the `extra["kotlin.version"]` e.g:
//
// extra["kotlin.version"] = "1.4.31"

repositories {
    mavenCentral()
    mavenLocal()
}

dependencies {
    implementation(platform("com.netflix.graphql.dgs:graphql-dgs-platform-dependencies:latest.release"))
    implementation("com.netflix.graphql.dgs:graphql-dgs-spring-boot-starter")
    implementation("com.netflix.graphql.dgs:graphql-dgs-extended-scalars")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.github.javafaker:javafaker:1.+")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.withType<com.netflix.graphql.dgs.codegen.gradle.GenerateJavaTask> {
    generateClient = true
    packageName = "com.example.demo.generated"
}

tasks.withType<JavaCompile> {
    java {
        targetCompatibility = JavaVersion.VERSION_1_8
        sourceCompatibility = JavaVersion.VERSION_1_8
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

