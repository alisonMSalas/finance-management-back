package uta.ec.finance_manager.service;

import uta.ec.finance_manager.dto.AccountDto;

import java.util.List;

public interface AccountService {
    AccountDto save(AccountDto accountDto);

    List<AccountDto> getAllByUser();

    AccountDto edit(AccountDto accountDto);

    void delete(Integer accountId);

    Double getTotalBalance();

    List<AccountDto> getAllByName(String name);
}
