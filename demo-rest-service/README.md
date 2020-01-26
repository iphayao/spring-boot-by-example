# Demo RESTful Web Service

> ตัวอย่างสำหรับสร้าง Web Service แบบง่ายๆ ด้วย Spring Boot

ในตัวอย่างนี้จะสร้าง web service สำหรับทักทาย ที่รับ HTTP GET request ด้วย URL path
```text
http://localhost:8080/greeting
```
และจะ response เป็นคำทักทายรูปแบบ JSON ดังนี้
```json
{"id":1, "content":"Hello World"}
```
โดยจะมี `id` เป็นลำดับของคำทักทาย และ `content` มีค่าเริ่มต้นเป็น `Hello World` 

และจะสามารถกำหนดชื่อของผู้ที่จะทักทายได้ด้วยพารามิเตอร์ `name` ใน query string ดังนี้
```text
http://localhost:8080/greeting?name=John
```
จะได้ response ที่เป็นคำทักทายที่มีชื่อของผู้ที่จะทักทาย
```json
{"id":1, "content":"Hello John"}
```

## สร้างโปรเจค
สร้างโปรเจคด้วย Spring Initializer โดยไปที่ `start.spring.io` โดยเลือก dependency ของโปรเจคเป็น `Spring Web` และในโปรเจคนี้จะเลือกเป็น Gradle project 

แล้วสร้างโปรเจคด้วยปุ่ม `Generate` จะได้เป็นไฟล์ zip โดยที่แตกไฟล์จะได้โปรเจค Spring Boot ที่พร้อมใช้งาน โดยที่ import โปรเจคที่สร้างขึ้นมาด้วย IDE (อย่างเช่น IntelliJ หรือ Eclipse เป็นต้น)  

```groovy
plugins {
	id 'org.springframework.boot' version '2.2.4.RELEASE'
	id 'io.spring.dependency-management' version '1.0.9.RELEASE'
	id 'java'
}

group = 'com.iphayao'
version = '0.0.1-SNAPSHOT'
sourceCompatibility = '1.8'

repositories {
	mavenCentral()
}

dependencies {
	implementation 'org.springframework.boot:spring-boot-starter-web'
	testImplementation('org.springframework.boot:spring-boot-starter-test') {
		exclude group: 'org.junit.vintage', module: 'junit-vintage-engine'
	}
}

test {
	useJUnitPlatform()
}
```

## สร้าง POJO class
เมื่อสร้างโปรเจคแล้วก็สร้าง POJO class เพื่อเป็นตัวแทนของ `Greeting` message

```java
package com.iphayao.demo;

public class Greeting {
    private final long id;
    private final String content;

    public Greeting(long id, String content) {
        this.id = id;
        this.content = content;
    }

    public long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }
}
```

## สร้าง Controller 
ใน RESTful Web Service นั้นจะต้องมี controller ที่ควบคุมการทำงานของ web service โดยที่จะจัดการกับ HTTP request method `GET`

```java
package com.iphayao.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

@RestController
public class GreetingController {
    private static final String template = "Hello %s";
    private final AtomicLong counter = new AtomicLong();

    @GetMapping("/greeting")
    public Greeting greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
        return new Greeting(counter.incrementAndGet(), String.format(template, name));
    }
}
```

## ทดสอบ
เมื่อสร้าง RESTful web service เรียบร้อยแล้ว ก็มาทดสอบการทำงานของโปรเจค ด้วยรันคำสั่ง จะเป็นการรัน web service ขึ้นมา

```cmd
./gradlew bootRun
```
หลังจาก web service ทำงานแล้ว ลองใช้ เครื่องมือทดสอบ อย่างเช่น POSTMAN โดยใส่ URL ดังนี้และเลือก method เป็น `GET`

```cmd
http://localhost:8080/greeting?name=John
```
จะได้ response ในรูปแบบ JSON ที่เป็นตัวแทนของ `Greeting`
```json
{
    "id": 1,
    "content": "Hello John"
}
```