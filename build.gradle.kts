plugins {
	id ("org.springframework.boot") version "3.4.1" apply false
	id("io.spring.dependency-management") version "1.1.7"
	java
}

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

allprojects {
	apply(plugin = "java")
	apply (plugin= "io.spring.dependency-management")

	repositories {
		mavenCentral()
	}


	dependencyManagement {
		overriddenByDependencies(false)

		dependencies {
			dependency("org.springframework.boot:spring-boot-starter-actuator:3.4.1")
			dependency ("org.springframework.boot:spring-boot-starter-web:3.4.1")
		}
	}
}

subprojects {
	apply(plugin = "java")

	tasks.withType<Test> {
		useJUnitPlatform()
	}

	dependencies {
		testImplementation ("org.junit.jupiter:junit-jupiter")

		testRuntimeOnly ("org.junit.platform:junit-platform-launcher")
	}
}