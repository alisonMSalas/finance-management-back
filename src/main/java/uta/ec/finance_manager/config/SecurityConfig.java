package uta.ec.finance_manager.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import uta.ec.finance_manager.config.auth.JwtRequestFilter;

import java.util.List;

/**
 * Configuración de seguridad de la aplicación.
 * <p>
 * Esta clase configura Spring Security para la aplicación, incluyendo:
 * - Autenticación basada en JWT
 * - Políticas de CORS
 * - Protección de endpoints
 * - Encriptación de contraseñas
 * - Gestión de sesiones sin estado (stateless)
 * </p>
 * 
 * @author Finance Manager Team
 * @version 1.0
 * @since 2024
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtRequestFilter jwtRequestFilter;

    /**
     * Bean para el codificador de contraseñas.
     * <p>
     * Utiliza BCrypt para la encriptación segura de contraseñas.
     * </p>
     * 
     * @return instancia de PasswordEncoder configurada con BCrypt
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Bean para el administrador de autenticación.
     * 
     * @param authenticationConfiguration configuración de autenticación
     * @return AuthenticationManager configurado
     * @throws Exception si hay un error en la configuración
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    /**
     * Configura la cadena de filtros de seguridad.
     * <p>
     * Esta configuración incluye:
     * - Deshabilitación de CSRF (no necesario para APIs REST stateless)
     * - Configuración de CORS para permitir peticiones desde el frontend
     * - Definición de endpoints públicos y protegidos
     * - Política de sesión sin estado (stateless)
     * - Filtro JWT para autenticación de peticiones
     * </p>
     * 
     * @param http objeto HttpSecurity para configurar
     * @return SecurityFilterChain configurada
     * @throws Exception si hay un error en la configuración
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(request -> {
                    CorsConfiguration config = new CorsConfiguration();
                    config.setAllowCredentials(true);
                    config.setAllowedOrigins(List.of("http://localhost:4200"));
                    config.setAllowedHeaders(List.of("*"));
                    config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
                    return config;
                }))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/register", "/api/login").permitAll()
                        .anyRequest().authenticated()
                )
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
