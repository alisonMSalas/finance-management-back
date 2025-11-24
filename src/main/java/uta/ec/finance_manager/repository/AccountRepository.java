package uta.ec.finance_manager.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uta.ec.finance_manager.entity.Account;

import java.util.List;
import java.util.Optional;

/**
 * Repositorio para la gestión de cuentas financieras en la base de datos.
 * <p>
 * Esta interfaz extiende JpaRepository para proporcionar operaciones CRUD
 * y métodos de consulta personalizados para la entidad Account.
 * </p>
 * 
 * @author Finance Manager Team
 * @version 1.0
 * @since 2024
 */
public interface AccountRepository extends JpaRepository<Account, Integer> {
    /**
     * Busca todas las cuentas de un usuario específico con ordenamiento.
     * 
     * @param userId identificador del usuario
     * @param sort criterio de ordenamiento
     * @return lista de cuentas del usuario ordenadas
     */
    List<Account> findByUserId(Integer userId, Sort sort);

    /**
     * Calcula el balance total de todas las cuentas de un usuario.
     * 
     * @param userId identificador del usuario
     * @return suma total de los balances de todas las cuentas del usuario
     */
    @Query("SELECT SUM(a.balance) FROM Account a WHERE a.user.id = :userId")
    Double getTotalBalanceByUserId(@Param("userId") Integer userId);

    /**
     * Busca una cuenta específica de un usuario.
     * 
     * @param id identificador de la cuenta
     * @param userId identificador del usuario propietario
     * @return Optional con la cuenta si existe y pertenece al usuario
     */
    Optional<Account> findOneByIdAndUserId(Integer id, Integer userId);

    /**
     * Busca cuentas por nombre (búsqueda parcial) para un usuario específico.
     * 
     * @param name texto a buscar en el nombre de la cuenta
     * @param userId identificador del usuario
     * @return lista de cuentas que contienen el texto en su nombre
     */
    List<Account> findAllByNameContainsAndUserId(String name, Integer userId);
}