package uta.ec.finance_manager.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uta.ec.finance_manager.entity.Transaction;
import uta.ec.finance_manager.enums.TransactionCategory;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Repositorio para la gestión de transacciones financieras en la base de datos.
 * <p>
 * Esta interfaz extiende JpaRepository para proporcionar operaciones CRUD
 * y métodos de consulta personalizados para la entidad Transaction.
 * </p>
 * 
 * @author Finance Manager Team
 * @version 1.0
 * @since 2024
 */
@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
    /**
     * Busca una transacción específica de un usuario.
     * 
     * @param id identificador de la transacción
     * @param userId identificador del usuario propietario
     * @return Optional con la transacción si existe y pertenece al usuario
     */
    Optional<Transaction> findOneByIdAndUserId(Integer id, Integer userId);

    /**
     * Busca todas las transacciones de un usuario con ordenamiento.
     * 
     * @param userId identificador del usuario
     * @param sort criterio de ordenamiento
     * @return lista de transacciones del usuario ordenadas
     */
    List<Transaction> findAllByUserId(Integer userId, Sort sort);

    /**
     * Busca transacciones por categoría dentro de un rango de fechas.
     * Útil para generar reportes y análisis de gastos por categoría.
     * 
     * @param category categoría de la transacción
     * @param start fecha de inicio del período
     * @param end fecha de fin del período
     * @return lista de transacciones que cumplen los criterios
     */
    List<Transaction> findAllByCategoryAndDateBetween(TransactionCategory category, Date start, Date end);
}
