apply (plugin= "org.springframework.boot")
apply(plugin = "java")

group = "com.techphile"
version = "0.0.1-SNAPSHOT"

dependencies {
	implementation ("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-actuator")
	implementation ("org.springframework.boot:spring-boot-starter-data-jpa")

	runtimeOnly ("org.postgresql:postgresql")
	runtimeOnly("io.micrometer:micrometer-registry-prometheus")

	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.springframework.kafka:spring-kafka-test")

}
