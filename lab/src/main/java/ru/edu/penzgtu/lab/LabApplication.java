package ru.edu.penzgtu.lab;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
    info = @Info(
        title = "PenzGTU Java Lab API",
        description = "API for labs",
        version = "1.0.0",
        contact = @Contact(
            name = "Student PenzGTU",
            email = "my_mail@dev"
        )
    )
)
public class LabApplication {
    public static void main(String[] args) {
        SpringApplication.run(LabApplication.class, args);
    }
}