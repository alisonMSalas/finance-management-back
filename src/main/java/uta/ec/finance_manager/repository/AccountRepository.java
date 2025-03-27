package uta.ec.finance_manager.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import uta.ec.finance_manager.entity.Account;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Integer> {
    List<Account> findByUserId(Integer userId);
}