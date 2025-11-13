plugins {
    java
    war
}

group = "ru.kaysiodl"
version = "1.0.0"

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

repositories {
    mavenCentral()
}

dependencies {
    compileOnly("jakarta.servlet:jakarta.servlet-api:6.1.0")
    implementation("jakarta.faces:jakarta.faces-api:4.1.2")
    implementation("jakarta.inject:jakarta.inject-api:2.0.1")
    implementation("jakarta.enterprise:jakarta.enterprise.cdi-api:5.0.0-M1")
    implementation("org.primefaces:primefaces:15.0.9:jakarta")

    implementation("jakarta.persistence:jakarta.persistence-api:3.2.0")
    implementation("jakarta.transaction:jakarta.transaction-api:2.0.1")
    implementation("org.eclipse.persistence:org.eclipse.persistence.jpa:5.0.0-B11")

    implementation("org.hibernate:hibernate-core:5.6.15.Final")
    implementation("org.hibernate:hibernate-entitymanager:5.6.15.Final")
    implementation("org.postgresql:postgresql:42.5.4")

    implementation("org.slf4j:slf4j-api:2.0.16")
    implementation("ch.qos.logback:logback-classic:1.5.16")

    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")

    compileOnly ("org.projectlombok:lombok:1.18.20")
    annotationProcessor ("org.projectlombok:lombok:1.18.20")
}

tasks.war {
    archiveFileName.set("lab3-web.war")
}
