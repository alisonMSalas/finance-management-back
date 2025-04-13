package uta.ec.finance_manager.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uta.ec.finance_manager.entity.Account;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account, Integer> {
    List<Account> findByUserId(Integer userId);

    @Query("SELECT SUM(a.balance) FROM Account a WHERE a.user.id = :userId")
    Double getTotalBalanceByUserId(@Param("userId") Integer userId);
}