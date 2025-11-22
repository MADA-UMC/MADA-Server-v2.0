import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
	kotlin("jvm") version "2.3.0-RC" //jdk 25 버전 지원
	kotlin("plugin.spring") version "2.3.0-RC" // 이전 Kotlin 버전은 1.9.25
	id("org.springframework.boot") version "3.5.7"
	id("io.spring.dependency-management") version "1.1.7"
}

group = "com.mada"
version = "0.0.1-SNAPSHOT"
description = "MADA Server version2"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(25)
	}

    // 현재는 Kotlin 1.9.25가 JVM 25 bytecode 타깃을 완전히 지원하지 않아
    // Java/Kotlin 컴파일 타깃을 21로 통일해둔 상태.
    // 나중에 Kotlin + Gradle + Spring Boot가 Java 25를 안정 지원하면
    //  - sourceCompatibility / targetCompatibility 를 VERSION_25 로 올리고
    //  - 아래 kotlin.compilerOptions.jvmTarget 도 JVM_25 로 변경할 예정.
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
}

extra["springModulithVersion"] = "1.4.4"

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-graphql")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.springframework.modulith:spring-modulith-starter-core")
	compileOnly("org.projectlombok:lombok")
	runtimeOnly("com.h2database:h2")
	runtimeOnly("org.postgresql:postgresql")
	annotationProcessor("org.projectlombok:lombok")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
	testImplementation("org.springframework.graphql:spring-graphql-test")
	testImplementation("org.springframework.modulith:spring-modulith-starter-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

dependencyManagement {
	imports {
		mavenBom("org.springframework.modulith:spring-modulith-bom:${property("springModulithVersion")}")
	}
}

kotlin {
	compilerOptions {
		freeCompilerArgs.addAll("-Xjsr305=strict")

        // 도구 체인이 JVM 25 타깃을 안정 지원하면
        // - jvmTarget 을 JVM_25 로 올리고
        // - 위 java 설정(source/targetCompatibility)도 VERSION_25 로 맞춘다.
        // 현재는 JDK 25 위에서 JVM 21 규격 bytecode로 빌드하는 구조.
        jvmTarget.set(JvmTarget.JVM_21)
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
