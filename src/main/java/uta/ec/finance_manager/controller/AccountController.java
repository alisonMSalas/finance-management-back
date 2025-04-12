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
        return this.accountService.createAccount(accountDto);
    }

    @GetMapping
    public List<AccountDto> getUserAccounts(){
        return this.accountService.getUserAccounts();
    }

    @PutMapping
    public AccountDto editAccount(@Valid @RequestBody AccountDto accountDto){
        return this.accountService.editAccount(accountDto);
    }

    @DeleteMapping
    public void deleteAccount(@RequestParam Integer accountId){
        this.accountService.delete(accountId);
    }

}
