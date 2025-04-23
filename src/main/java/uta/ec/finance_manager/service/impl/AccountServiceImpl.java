package uta.ec.finance_manager.service.impl;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import uta.ec.finance_manager.dto.AccountDto;
import uta.ec.finance_manager.entity.Account;
import uta.ec.finance_manager.repository.AccountRepository;
import uta.ec.finance_manager.repository.UserRepository;
import uta.ec.finance_manager.service.AccountService;
import uta.ec.finance_manager.util.UserUtil;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final UserUtil userUtil;

    @Override
    public AccountDto save(AccountDto accountDto) {
        Integer userId = userUtil.getUserId();
        accountDto.setUserId(userId);
        return accountToDto(accountRepository.save(dtoToAccount(accountDto)));
    }

    @Override
    public List<AccountDto> getAllByUser() {
        Integer userId = userUtil.getUserId();
        List<Account> list = accountRepository.findByUserId(userId, Sort.by("id"));
        return list.stream().map(this::accountToDto).toList();
    }

    @Override
    public AccountDto edit(AccountDto accountDto) {
        Integer userId = userUtil.getUserId();

        Account account = accountRepository.findOneByIdAndUserId(accountDto.getId(), userId)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "No existe la cuenta"));

        account.setName(accountDto.getName());
        account.setType(accountDto.getType());
        account.setBalance(accountDto.getBalance());

        return accountToDto(accountRepository.save(account));
    }

    @Override
    public void delete(Integer accountId) {
        Integer userId = userUtil.getUserId();

        Account account = accountRepository.findOneByIdAndUserId(accountId, userId).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "No existe la cuenta"));
        accountRepository.delete(account);
    }

    @Override
    public Double getTotalBalance() {
        Integer userId = userUtil.getUserId();
        return accountRepository.getTotalBalanceByUserId(userId);
    }

    @Override
    public List<AccountDto> getAllByName(String name) {
        Integer userId = userUtil.getUserId();
        return accountRepository.findAllByNameContainsAndUserId(name, userId)
                .stream().map(this::accountToDto).toList();
    }

    private Account dtoToAccount(AccountDto accountDto) {
        Account account = modelMapper.map(accountDto, Account.class);
        account.setUser(userRepository.findById(accountDto.getUserId()).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "No existe el usuario")));
        return account;
    }

    private AccountDto accountToDto(Account account) {
        AccountDto accountDto = modelMapper.map(account, AccountDto.class);
        accountDto.setUserId(account.getUser().getId());
        return accountDto;
    }
}
