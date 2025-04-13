package uta.ec.finance_manager.repository;


import org.hibernate.boot.model.source.spi.Sortable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uta.ec.finance_manager.entity.Account;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Integer> {
    List<Account> findByUserId(Integer userId, Sort sort);

    @Query("SELECT SUM(a.balance) FROM Account a WHERE a.user.id = :userId")
    Double getTotalBalanceByUserId(@Param("userId") Integer userId);

    Optional<Account> findOneByIdAndUserId(Integer id, Integer userId);
}