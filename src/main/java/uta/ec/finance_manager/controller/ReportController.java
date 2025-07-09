package uta.ec.finance_manager.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uta.ec.finance_manager.dto.*;
import uta.ec.finance_manager.entity.Account;
import uta.ec.finance_manager.service.AccountService;
import uta.ec.finance_manager.service.BudgetService;
import uta.ec.finance_manager.service.TransactionService;

import java.util.List;

@RestController
@RequestMapping("/report")
@RequiredArgsConstructor
public class ReportController {
    private final TransactionService transactionService;
    private final AccountService accountService;
    private final BudgetService budgetService;

    @GetMapping
    public ReportDto getReport() {
        ReportDto reportDto = new ReportDto();

        List<AccountDto> accountList = accountService.getAllByUser();
        List<TransactionDto> transactionDtos = transactionService.getAllByUser();
        List<BudgetDto> budgetDtos = budgetService.getAll();

        double totalCuentas = accountList.stream()
                .mapToDouble(AccountDto::getBalance)
                .sum();

        // Total ingresos y egresos
        double totalIngresos = transactionDtos.stream()
                .filter(t -> "Gasto".equalsIgnoreCase(t.getType()))
                .mapToDouble(TransactionDto::getAmount)
                .sum();

        double totalGastos = transactionDtos.stream()
                .filter(t -> "Ingreso".equalsIgnoreCase(t.getType()))
                .mapToDouble(TransactionDto::getAmount)
                .sum();

        /* Total presupuestos
        double totalPresupuestos = budgetDtos.stream()
                .mapToDouble(BudgetDto::getAmount)
                .sum();*/

        // Asignar al DTO
        reportDto.setAccounts(totalCuentas);
        reportDto.setIncomes(totalIngresos);
        reportDto.setExpenses(totalGastos);
        reportDto.setBudget(0.0);
        return reportDto;
    }
}

