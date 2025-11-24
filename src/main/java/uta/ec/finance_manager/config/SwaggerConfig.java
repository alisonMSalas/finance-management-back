package uta.ec.finance_manager.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.Components;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuración de Swagger/OpenAPI para la documentación de la API REST.
 * 
 * Esta clase configura la interfaz de usuario de Swagger y define la información
 * general de la API, incluyendo título, descripción, versión y esquemas de seguridad.
 * 
 * @author Finance Management Team
 * @version 1.0
 * @since 2025-11-24
 */
@Configuration
public class SwaggerConfig {

    /**
     * Configura el bean de OpenAPI con información general de la API y esquema de seguridad JWT.
     * 
     * @return OpenAPI configurado con información de la API y seguridad
     */
    @Bean
    public OpenAPI customOpenAPI() {
        final String securitySchemeName = "bearerAuth";
        
        return new OpenAPI()
                .info(new Info()
                        .title("Finance Management API")
                        .description("API REST para la gestión de finanzas personales. " +
                                "Incluye módulos de cuentas, transacciones, presupuestos, " +
                                "metas de ahorro, inversiones y automatizaciones.")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Finance Management Team")
                                .email("finance@uta.edu.ec")
                                .url("https://github.com/melanieAlban/finance-management"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0.html")))
                .addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
                .components(new Components()
                        .addSecuritySchemes(securitySchemeName,
                                new SecurityScheme()
                                        .name(securitySchemeName)
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")
                                        .description("Ingrese el token JWT (sin 'Bearer' al inicio)")));
    }
}
