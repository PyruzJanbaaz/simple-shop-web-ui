# Simple Shop WebApp
Simple web application created by spring boot and thymeleaf!


I create a Spring Boot project(Web Application) for a simple shop REST APIs. You need the following tools and technologies to develop the same.
- Spring-Boot 2.1.6.RELEASE
- Spring-Boot-Thymeleaf 2.1.6.RELEASE
- Lombok 1.18.6
- JavaSE 11
- Maven 3.3.9


# Dependencies
Open the pom.xml file for spring-aop configuration:

      <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.1.6.RELEASE</version>
        <relativePath/> <!-- lookup parent from repository -->
      </parent>
      
and dpendencies:

       <!-- SPRING -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
       <!-- PROJECT LOMBOK -->
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
        </dependency>



# Features
Users be able to search for products per product name, price range, rate, etc.


# Usage
Run the project and go to http://localhost:8080 on your browser!

