package uta.ec.finance_manager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uta.ec.finance_manager.entity.SavingGoal;
import uta.ec.finance_manager.enums.GoalStatus;

import java.util.List;

@Repository
public interface SavingGoalRepository extends JpaRepository<SavingGoal, Integer> {
    List<SavingGoal> findByUserId(Integer userId);
    List<SavingGoal> findByGoalStatus(GoalStatus goalStatus);
}
