package uta.ec.finance_manager.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uta.ec.finance_manager.dto.SavingGoalDto;
import uta.ec.finance_manager.service.SavingGoalService;

import java.util.List;

@RestController
@RequestMapping("saving")
@RequiredArgsConstructor
public class SavingGoalController
{
    private final SavingGoalService savingGoalService;
    @PostMapping
    public SavingGoalDto save(@Valid @RequestBody SavingGoalDto savingGoalDto) {
        return savingGoalService.save(savingGoalDto);
    }

    @PutMapping
    public SavingGoalDto update(@Valid @RequestBody SavingGoalDto savingGoalDto) {
        return savingGoalService.update(savingGoalDto);
    }

    @DeleteMapping
    public void delete(@RequestParam Integer id) {
        savingGoalService.delete(id);
    }

    @GetMapping()
    public SavingGoalDto getById(@PathVariable Integer id) {
        return savingGoalService.getById(id);
    }

    @GetMapping("/user")
    public List<SavingGoalDto> getByUser() {
        return savingGoalService.getByUser();
    }
}
