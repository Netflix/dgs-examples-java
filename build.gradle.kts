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
	id("org.springframework.boot") version "2.4.0"
	id("io.spring.dependency-management") version "1.0.11.RELEASE"
	id("com.netflix.dgs.codegen") version "4.4.3"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
	mavenCentral()
}

dependencies {
	implementation(platform("com.netflix.graphql.dgs:graphql-dgs-platform-dependencies:3.10.2"))
	implementation("com.netflix.graphql.dgs:graphql-dgs-spring-boot-starter")
	implementation("com.graphql-java:graphql-java-extended-scalars:1.0.+")
	implementation("com.github.javafaker:javafaker:1.+")

	implementation("org.springframework.boot:spring-boot-starter-web")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.withType<com.netflix.graphql.dgs.codegen.gradle.GenerateJavaTask> {
	generateClient = true
	packageName = "com.example.demo.generated"
}

tasks.withType<JavaCompile> {
	java {
		targetCompatibility = JavaVersion.VERSION_11
		sourceCompatibility = JavaVersion.VERSION_11
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}

