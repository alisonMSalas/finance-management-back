package uta.ec.finance_manager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uta.ec.finance_manager.entity.Automation;

public interface AutomationRepository  extends JpaRepository<Automation, Integer> {
}
