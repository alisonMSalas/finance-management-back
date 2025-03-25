package uta.ec.finance_manager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uta.ec.finance_manager.entity.Budget;

public interface BudgetRepository extends JpaRepository<Budget, Integer> {
}
