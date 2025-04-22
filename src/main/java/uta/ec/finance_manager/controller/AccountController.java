package uta.ec.finance_manager.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uta.ec.finance_manager.dto.AccountDto;
import uta.ec.finance_manager.service.AccountService;

import java.util.List;

@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;

    @PostMapping
    public AccountDto createAccount(@Valid @RequestBody AccountDto accountDto){
        return this.accountService.save(accountDto);
    }

    @GetMapping
    public List<AccountDto> getUserAccounts(){
        return accountService.getAllByUser();
    }

    @PutMapping
    public AccountDto editAccount(@Valid @RequestBody AccountDto accountDto){
        return accountService.edit(accountDto);
    }

    @DeleteMapping
    public void deleteAccount(@RequestParam Integer accountId){
        accountService.delete(accountId);
    }

    @GetMapping("/total-balance")
    public Double getTotalBalance() {
        return accountService.getTotalBalance();
    }

    @GetMapping("/name")
    public List<AccountDto> getAllByName(@PathVariable String name) {
        return accountService.getAllByName(name);
    }
}
