package uta.ec.finance_manager;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * Clase principal de la aplicación Finance Manager.
 * <p>
 * Esta aplicación proporciona un sistema completo de gestión financiera personal
 * que permite a los usuarios administrar cuentas, transacciones, presupuestos,
 * inversiones y metas de ahorro.
 * </p>
 * 
 * @author Finance Manager Team
 * @version 1.0
 * @since 2024
 */
@SpringBootApplication
public class FinanceManagerApplication {

	/**
	 * Método principal que inicia la aplicación Spring Boot.
	 * 
	 * @param args argumentos de línea de comandos
	 */
	public static void main(String[] args) {
		SpringApplication.run(FinanceManagerApplication.class, args);
	}

	/**
	 * Bean de ModelMapper para mapeo automático entre entidades y DTOs.
	 * 
	 * @return instancia configurada de ModelMapper
	 */
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
}
