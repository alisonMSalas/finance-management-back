package uta.ec.finance_manager.service;

import uta.ec.finance_manager.dto.SavingGoalDto;

import java.util.List;

public interface SavingGoalService {
    SavingGoalDto getById(int id);
    List<SavingGoalDto> getByUser();
    SavingGoalDto save(SavingGoalDto savingGoalDto);
    SavingGoalDto update (SavingGoalDto savingGoalDto);
    void delete(int id);
}
