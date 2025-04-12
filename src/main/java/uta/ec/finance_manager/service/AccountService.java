package uta.ec.finance_manager.service;

import uta.ec.finance_manager.dto.AccountDto;

import java.util.List;

public interface AccountService {
    AccountDto createAccount(AccountDto accountDto);

    List<AccountDto> getUserAccounts();

    AccountDto editAccount(AccountDto accountDto);

    void delete(Integer accountId);
}
