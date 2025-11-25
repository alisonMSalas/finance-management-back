package uta.ec.finance_manager;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @file FinanceManagerApplication.java
 * @brief Clase principal de la aplicación Finance Manager
 *
 * Esta clase es el punto de entrada de la aplicación Spring Boot para
 * la gestión de finanzas personales. Configura los componentes principales
 * y habilita la programación de tareas.
 *
 * @author Finance Manager Team
 * @version 1.0
 */
@EnableScheduling
@SpringBootApplication
public class FinanceManagerApplication {

	/**
	 * @brief Método principal que inicia la aplicación Spring Boot
	 * @param args Argumentos de línea de comandos
	 */
	public static void main(String[] args) {
		SpringApplication.run(FinanceManagerApplication.class, args);
	}

	/**
	 * @brief Crea y configura el bean ModelMapper
	 *
	 * ModelMapper se utiliza para mapear entre entidades y DTOs
	 * en toda la aplicación.
	 *
	 * @return Instancia configurada de ModelMapper
	 */
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
}
