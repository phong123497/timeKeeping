package com.timekeeping.common.config;

import com.timekeeping.common.constant.ApplicationConstant;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;

/**
 * @author minhtq2 on 24/10/2023
 * @project TimeKeeping
 */
@Configuration
public class SwaggerConfiguration {

    private static final String SECURITY_SCHEME_NAME = "Bearer oAuth Token";

    @Value("${server.servlet.context-path}")
    private String contextPath;

    @Value("${server.management.app.url}")
    private String serverManagementAppUrl;

    @Value("${server.auth.app.url}")
    private String serverAuthAppUrl;

    /**
     * Open API Configuration Bean
     *
     * @return OpenAPI
     */
    @Bean
    public OpenAPI openApiConfiguration(
    ) {
        String serverAppUrl = "";
        if(ApplicationConstant.ContextPath.AUTH_CONTEXT_PATH.equalsIgnoreCase(contextPath)){
            serverAppUrl = serverAuthAppUrl;
        } else if(ApplicationConstant.ContextPath.MANAGEMENT_CONTEXT_PATH.equalsIgnoreCase(contextPath)) {
            serverAppUrl = serverManagementAppUrl;
        }

        return new OpenAPI()
                .addServersItem(new Server().url(serverAppUrl + contextPath))
                .addSecurityItem(new SecurityRequirement().addList(SECURITY_SCHEME_NAME))
                .components(
                        new Components()
                                .addSecuritySchemes(SECURITY_SCHEME_NAME,
                                        new SecurityScheme()
                                                .name(SECURITY_SCHEME_NAME)
                                                .type(SecurityScheme.Type.HTTP)
                                                .scheme("bearer")
                                                .bearerFormat("JWT")
                                )
                )
                .info(new Info()
                        .termsOfService("Terms of service")
                        .license(getLicense())
                        .contact(getContact())
                );
    }

    /**
     * Contact details for the developer(s)
     *
     * @return Contact
     */
    private Contact getContact() {
        Contact contact = new Contact();
        contact.setEmail("support@timekeeping.vn"); // TODO... settings temp value
        contact.setName("TimeKeeping");
        contact.setUrl("https://qa.timekeeping.vn"); // TODO... settings temp value
        contact.setExtensions(Collections.emptyMap());
        return contact;
    }

    /**
     * License creation
     *
     * @return License
     */
    private License getLicense() {
        License license = new License();
        license.setName("Apache License, Version 2.0");
        license.setUrl("http://www.apache.org/licenses/LICENSE-2.0");
        license.setExtensions(Collections.emptyMap());
        return license;
    }
}
