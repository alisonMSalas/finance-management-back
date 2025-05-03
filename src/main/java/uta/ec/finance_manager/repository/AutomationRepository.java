package uta.ec.finance_manager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uta.ec.finance_manager.entity.Automation;

import java.util.Date;
import java.util.List;

public interface AutomationRepository  extends JpaRepository<Automation, Integer> {
    List<Automation> findByUserId(Integer userId);
    @Query("SELECT a FROM Automation a JOIN FETCH a.account WHERE a.startDate <= :date")
    List<Automation> findByStartDateBeforeWithAccount(@Param("date") Date date);

}
