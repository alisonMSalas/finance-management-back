package uta.ec.finance_manager.service.impl;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import uta.ec.finance_manager.dto.TransactionDto;
import uta.ec.finance_manager.entity.Transaction;
import uta.ec.finance_manager.repository.AccountRepository;
import uta.ec.finance_manager.repository.TransactionRepository;
import uta.ec.finance_manager.service.TransactionService;
import uta.ec.finance_manager.util.UserUtil;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;
    private final ModelMapper modelMapper;
    private final UserUtil userUtil;

    @Override
    public TransactionDto save(TransactionDto dto) {
        Integer userId = userUtil.getUserId();
        Transaction transaction = dtoToTransaction(dto, userId);
        return transactionToDto(transactionRepository.save(transaction));
    }

    @Override
    public TransactionDto edit(Integer transactionId, TransactionDto dto) {
        Integer userId = userUtil.getUserId();
        Transaction transaction = transactionRepository.findOneByIdAndUserId(transactionId, userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No existe la transaccion"));

        if (dto.getId() == null || !dto.getId().equals(userId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No existe la transaccion");
        }

        modelMapper.map(dto, transaction);
        return transactionToDto(transactionRepository.save(transaction));
    }

    private TransactionDto transactionToDto(Transaction transaction) {
        TransactionDto transactionDto = modelMapper.map(transaction, TransactionDto.class);
        transactionDto.setAccountId(transaction.getAccount().getId());
        return transactionDto;
    }

    private Transaction dtoToTransaction(TransactionDto dto, Integer userId) {
        Transaction transaction = modelMapper.map(dto, Transaction.class);
        transaction.setAccount(accountRepository.findOneByIdAndUserId(dto.getAccountId(), userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No existe la cuenta")));
        return transaction;
    }
}
