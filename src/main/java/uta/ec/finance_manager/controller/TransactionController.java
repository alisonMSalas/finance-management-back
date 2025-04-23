package uta.ec.finance_manager.controller;

import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uta.ec.finance_manager.dto.AccountDto;
import uta.ec.finance_manager.dto.TransactionDto;
import uta.ec.finance_manager.service.AccountService;
import uta.ec.finance_manager.service.TransactionService;

import java.util.List;

@RestController
@RequestMapping("/transaction")
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionService transactionService;

    @PostMapping
    public TransactionDto save(@Valid @RequestBody TransactionDto transactionDto) {
        return transactionService.save(transactionDto);
    }

    @PutMapping
    public TransactionDto edit(@RequestParam Integer transactionId, @Valid @RequestBody TransactionDto transactionDto) {
        return transactionService.edit(transactionId, transactionDto);
    }

    @GetMapping
    public List<TransactionDto> getAllByUSer() {
        return  transactionService.getAllByUser();
    }
}
