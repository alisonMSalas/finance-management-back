package uta.ec.finance_manager.service;

import jakarta.transaction.Transactional;
import org.springframework.scheduling.annotation.Scheduled;
import uta.ec.finance_manager.dto.SavingGoalDto;

import java.util.List;

public interface SavingGoalService {
    SavingGoalDto getById(int id);
    List<SavingGoalDto> getByUser();
    SavingGoalDto save(SavingGoalDto savingGoalDto);
    SavingGoalDto update (SavingGoalDto savingGoalDto);
    void delete(int id);
    void updateStreaks();
}
