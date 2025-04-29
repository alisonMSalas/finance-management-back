package uta.ec.finance_manager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uta.ec.finance_manager.entity.Automation;

import java.util.Date;
import java.util.List;

public interface AutomationRepository  extends JpaRepository<Automation, Integer> {
    List<Automation> findByUserId(Integer userId);
    List<Automation> findByStartDateBefore(Date date);
}
