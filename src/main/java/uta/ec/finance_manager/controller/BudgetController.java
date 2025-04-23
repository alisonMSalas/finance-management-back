package uta.ec.finance_manager.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uta.ec.finance_manager.dto.BudgetDto;
import uta.ec.finance_manager.service.BudgetService;

import java.util.List;

@RestController
@RequestMapping("budget")
@RequiredArgsConstructor
public class BudgetController {
    private final BudgetService budgetService;

    @PostMapping
    public BudgetDto save(@Valid @RequestBody BudgetDto budgetDto) {
        return budgetService.save(budgetDto);
    }

    @GetMapping
    public List<BudgetDto> getAll() {
        return budgetService.getAll();
    }

    @PutMapping
    public BudgetDto update(@Valid @RequestBody BudgetDto budgetDto) {
        return budgetService.update(budgetDto);
    }

    @DeleteMapping
    public void delete(@RequestParam Integer budgetId) {
        budgetService.delete(budgetId);
    }
}
