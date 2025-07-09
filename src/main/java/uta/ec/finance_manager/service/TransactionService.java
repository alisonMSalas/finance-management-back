package uta.ec.finance_manager.service;

import uta.ec.finance_manager.dto.TransactionDto;

import java.util.List;

public interface TransactionService {
    TransactionDto save(TransactionDto dto);
    TransactionDto save(TransactionDto dto, Integer userId);
    TransactionDto edit(Integer transactionId, TransactionDto dto);
    List<TransactionDto> getAllByUser();
}
