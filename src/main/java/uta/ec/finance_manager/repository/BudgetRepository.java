package uta.ec.finance_manager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uta.ec.finance_manager.entity.Budget;
import uta.ec.finance_manager.enums.BudgetPeriod;
import uta.ec.finance_manager.enums.TransactionCategory;

import java.util.List;
import java.util.Optional;

public interface BudgetRepository extends JpaRepository<Budget, Integer> {
    List<Budget> findAllByUserId(Integer userId);
    Optional<Budget> findByUserIdAndCategoryAndPeriod(Integer userId, TransactionCategory category, BudgetPeriod period);
    List<Budget> findByUserIdAndCategory(Integer userId, TransactionCategory category);
}
