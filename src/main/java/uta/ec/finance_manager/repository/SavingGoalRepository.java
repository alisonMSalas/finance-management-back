package uta.ec.finance_manager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uta.ec.finance_manager.entity.SavingGoal;

public interface SavingGoalRepository extends JpaRepository<SavingGoal, Integer> {
}
