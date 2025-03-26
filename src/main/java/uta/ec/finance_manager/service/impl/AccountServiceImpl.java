package uta.ec.finance_manager.service.impl;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import uta.ec.finance_manager.dto.AccountDto;
import uta.ec.finance_manager.entity.Account;
import uta.ec.finance_manager.repository.AccountRepository;
import uta.ec.finance_manager.repository.UserRepository;
import uta.ec.finance_manager.service.AccountService;

import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    @Override
    public AccountDto createAccount(AccountDto accountDto) {
        return accountToDto(this.accountRepository.save(dtoToAccount(accountDto)));
    }

    @Override
    public List<AccountDto> getUserAccounts(Integer userId) {
        List<Account> list = this.accountRepository.findByUserId(userId);
        return list.stream().map(this::accountToDto).toList();
    }

    private Account dtoToAccount(AccountDto accountDto){
        Account account = this.modelMapper.map(accountDto, Account.class);
        account.setUser(this.userRepository.findById(accountDto.getUserId()).orElseThrow( () ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Not User Found")));
        return account;
    }

    private AccountDto accountToDto(Account account){
        AccountDto accountDto = this.modelMapper.map(account, AccountDto.class);
        accountDto.setUserId(account.getId());
        return accountDto;
    }
}
