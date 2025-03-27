package uta.ec.finance_manager.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uta.ec.finance_manager.dto.BudgetDto;
import uta.ec.finance_manager.service.BudgetService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BudgetController {
    private final BudgetService budgetService;

    @PostMapping
    public BudgetDto save(@RequestBody BudgetDto budgetDto) {
        return budgetService.save(budgetDto);
    }

    @GetMapping
    public List<BudgetDto> getAllByUserId(@RequestParam Integer userId) {
        return budgetService.getAllByUserId(userId);
    }

    @PutMapping
    public BudgetDto update(@RequestParam BudgetDto budgetDto) {
        return budgetService.update(budgetDto);
    }

    @DeleteMapping
    public void delete(@RequestParam Integer budgetId) {
        budgetService.delete(budgetId);
    }
}
