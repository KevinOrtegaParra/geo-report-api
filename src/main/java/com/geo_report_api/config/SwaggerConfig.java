package com.geo_report_api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;

@SecurityScheme(
        name = "Authorization",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer"
)
@Configuration
public class SwaggerConfig {
     @Bean
    public OpenAPI apiDocConfig() {

        return new OpenAPI()
                .info(new Info()
                        .title("Incident Reporting API")
                        .description("This API allows reporting, consulting, and managing citizen incidents with geographical location.")
                        .version("1.0.0")
                        .contact(
                                new Contact()
                                        .name("Kevin Ortega")
                                        .email("kevinantonioortegaparra10@gmail.com")
                                        
                        )
                )
                .externalDocs(new ExternalDocumentation()
                        .description("linkedin")
                        .url("www.linkedin.com/in/kevin-antonio-ortega-parra-602206327"));
    }
}
