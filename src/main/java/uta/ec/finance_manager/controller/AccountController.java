package uta.ec.finance_manager.controller;

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
    @PostMapping()
    public AccountDto createAccount(@RequestBody AccountDto accountDto){
        return this.accountService.createAccount(accountDto);
    }

    @GetMapping()
    public List<AccountDto> getUserAccounts(@RequestParam Integer userId){
        return this.accountService.getUserAccounts(userId);
    }

    @PutMapping()
    public AccountDto editAccount(@RequestBody AccountDto accountDto){
        return this.accountService.editAccount(accountDto);
    }

    @DeleteMapping()
    public void deleteAccount(@RequestParam Integer accountId){
        this.accountService.delete(accountId);
    }

}
