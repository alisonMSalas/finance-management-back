package uta.ec.finance_manager.service.impl;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import uta.ec.finance_manager.dto.BudgetDto;
import uta.ec.finance_manager.entity.Budget;
import uta.ec.finance_manager.entity.Transaction;
import uta.ec.finance_manager.enums.BudgetPeriod;
import uta.ec.finance_manager.repository.BudgetRepository;
import uta.ec.finance_manager.repository.TransactionRepository;
import uta.ec.finance_manager.repository.UserRepository;
import uta.ec.finance_manager.service.BudgetService;
import uta.ec.finance_manager.service.MessageService;
import uta.ec.finance_manager.util.DateUtil;
import uta.ec.finance_manager.util.UserUtil;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BudgetServiceImpl implements BudgetService {
    private final BudgetRepository budgetRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final TransactionRepository transactionRepository;
    private final MessageService messageService;
    private final DateUtil dateUtil;
    private final UserUtil userUtil;

    @Override
    public BudgetDto save(BudgetDto budgetDto) {
        budgetRepository.findByUserIdAndCategoryAndPeriod(userUtil.getUserId(), budgetDto.getCategory(), budgetDto.getPeriod())
                .ifPresent(budget -> {
                    throw new ResponseStatusException(HttpStatus.CONFLICT, "El presupuesto ya existe");
                });

        Date start;
        Date end;

        if (budgetDto.getPeriod().equals(BudgetPeriod.SEMANAL)) {
            start = dateUtil.getStartOfWeek();
            end = dateUtil.getEndOfWeek();
        } else if (budgetDto.getPeriod().equals(BudgetPeriod.MENSUAL)) {
            start = dateUtil.getStartOfMonth();
            end = dateUtil.getEndOfMonth();
        } else {
            start = dateUtil.getStartOfYear();
            end = dateUtil.getEndOfYear();
        }

        budgetDto.setCurrentAmount(transactionRepository
                .findAllByCategoryAndDateBetween(budgetDto.getCategory(), start, end).stream()
                .mapToDouble(transaction -> transaction.getType().equals("Gasto") ? transaction.getAmount() : 0)
                .sum()
        );

        return budgetToDto(budgetRepository.save(dtoToBudget(budgetDto)));
    }

    @Override
    public List<BudgetDto> getAll() {
        return budgetRepository.findAllByUserId(userUtil.getUserId()).stream().map(budget -> {
            Date start;
            Date end;

            if (budget.getPeriod().equals(BudgetPeriod.SEMANAL)) {
                start = dateUtil.getStartOfWeek();
                end = dateUtil.getEndOfWeek();
            } else if (budget.getPeriod().equals(BudgetPeriod.MENSUAL)) {
                start = dateUtil.getStartOfMonth();
                end = dateUtil.getEndOfMonth();
            } else {
                start = dateUtil.getStartOfYear();
                end = dateUtil.getEndOfYear();
            }

            budget.setCurrentAmount(transactionRepository
                    .findAllByCategoryAndDateBetween(budget.getCategory(), start, end).stream()
                    .mapToDouble(transaction -> transaction.getType().equals("Gasto") ? transaction.getAmount() : 0)
                    .sum()
            );

            return budgetToDto(budget);
        }).toList();
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
        budget.setUser(userRepository.findById(userUtil.getUserId()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No existe el usuario")
        ));

        return budget;
    }

    private BudgetDto budgetToDto(Budget budget) {
        BudgetDto budgetDto = modelMapper.map(budget, BudgetDto.class);
        budgetDto.setUserId(budget.getUser().getId());

        return budgetDto;
    }
}

