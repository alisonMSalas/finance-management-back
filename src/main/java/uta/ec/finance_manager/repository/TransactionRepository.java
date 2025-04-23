package uta.ec.finance_manager.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uta.ec.finance_manager.entity.Transaction;
import uta.ec.finance_manager.enums.TransactionCategory;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
    Optional<Transaction> findOneByIdAndUserId(Integer id, Integer userId);

    List<Transaction> findAllByUserId(Integer userId, Sort sort);

    List<Transaction> findAllByCategoryAndDateBetween(TransactionCategory category, Date start, Date end);
}
