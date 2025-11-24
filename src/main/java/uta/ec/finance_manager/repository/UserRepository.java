package uta.ec.finance_manager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uta.ec.finance_manager.entity.User;

/**
 * Repositorio para la gestión de usuarios en la base de datos.
 * <p>
 * Esta interfaz extiende JpaRepository para proporcionar operaciones CRUD
 * básicas y métodos de consulta personalizados para la entidad User.
 * </p>
 * 
 * @author Finance Manager Team
 * @version 1.0
 * @since 2024
 */
public interface UserRepository extends JpaRepository<User, Integer> {
    /**
     * Busca un usuario por su correo electrónico.
     * 
     * @param email correo electrónico del usuario a buscar
     * @return usuario encontrado o null si no existe
     */
    User findByEmail(String email);
}
