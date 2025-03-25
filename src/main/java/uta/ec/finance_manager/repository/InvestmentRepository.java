package uta.ec.finance_manager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uta.ec.finance_manager.entity.Investment;

public interface InvestmentRepository  extends JpaRepository<Investment, Integer> {
}
