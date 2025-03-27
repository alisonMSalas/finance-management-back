package uta.ec.finance_manager.service.impl;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import uta.ec.finance_manager.dto.BudgetDto;
import uta.ec.finance_manager.entity.Budget;
import uta.ec.finance_manager.repository.BudgetRepository;
import uta.ec.finance_manager.repository.UserRepository;
import uta.ec.finance_manager.service.BudgetService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BudgetServiceImpl implements BudgetService {
    private final BudgetRepository budgetRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    public BudgetDto save(BudgetDto budgetDto) {
        return budgetToDto(budgetRepository.save(dtoToBudget(budgetDto)));
    }

    @Override
    public List<BudgetDto> getAllByUserId(Integer userId) {
        return budgetRepository.findAllByUserId(userId).stream().map(this::budgetToDto).toList();
    }

    @Override
    public BudgetDto update(BudgetDto budgetDto) {
        Budget budget = budgetRepository.findById(budgetDto.getId()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND)
        );

        modelMapper.map(budgetDto, budget);

        return budgetToDto(budgetRepository.save(budget));
    }

    @Override
    public void delete(Integer budgetId) {
        budgetRepository.findById(budgetId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND)
        );

        budgetRepository.deleteById(budgetId);
    }

    private Budget dtoToBudget(BudgetDto budgetDto) {
        Budget budget = modelMapper.map(budgetDto, Budget.class);
        budget.setUser(userRepository.findById(budgetDto.getUserId()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND)
        ));

        return budget;
    }

    private BudgetDto budgetToDto(Budget budget) {
        BudgetDto budgetDto = modelMapper.map(budget, BudgetDto.class);
        budgetDto.setUserId(budget.getUser().getId());

        return budgetDto;
    }
}

