plugins {
    java
}

group = "org.example"
version = "1.0-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_21
}

repositories {
    mavenCentral()
}

dependencies {
    // PostgreSQL драйвер
    implementation("org.postgresql:postgresql:42.6.0")

    // Apache POI для работы с Excel
    implementation("org.apache.poi:poi-ooxml:5.2.2")

    // Apache XMLBeans для работы с XML
    implementation("org.apache.xmlbeans:xmlbeans:5.1.1")

    // Commons Logging (логгирование)
    implementation("commons-logging:commons-logging:1.2")
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

tasks.withType<JavaExec> {
    systemProperty("file.encoding", "UTF-8")
}

