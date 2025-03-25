package uta.ec.finance_manager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uta.ec.finance_manager.entity.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
}
