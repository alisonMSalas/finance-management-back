package uta.ec.finance_manager.service;

import uta.ec.finance_manager.dto.TransactionDto;

public interface TransactionService {
    TransactionDto save(TransactionDto dto);
    TransactionDto edit(Integer transactionId, TransactionDto dto);
}
