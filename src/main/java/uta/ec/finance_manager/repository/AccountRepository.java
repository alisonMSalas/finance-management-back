package uta.ec.finance_manager.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import uta.ec.finance_manager.entity.Account;

public interface AccountRepository extends JpaRepository<Account, Integer> {
}