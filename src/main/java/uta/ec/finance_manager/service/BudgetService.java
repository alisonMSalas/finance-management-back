package uta.ec.finance_manager.service;

import uta.ec.finance_manager.dto.BudgetDto;

import java.util.List;

public interface BudgetService {

    BudgetDto save(BudgetDto budgetDto);

    List<BudgetDto> getAll();

    BudgetDto update(BudgetDto budgetDto);

    void delete(Integer budgetId);
}
