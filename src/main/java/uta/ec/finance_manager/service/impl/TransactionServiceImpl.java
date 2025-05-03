package uta.ec.finance_manager.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import uta.ec.finance_manager.dto.TransactionDto;
import uta.ec.finance_manager.entity.Transaction;
import uta.ec.finance_manager.repository.AccountRepository;
import uta.ec.finance_manager.repository.TransactionRepository;
import uta.ec.finance_manager.repository.UserRepository;
import uta.ec.finance_manager.service.TransactionService;
import uta.ec.finance_manager.util.UserUtil;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;
    private final ModelMapper modelMapper;
    private final UserUtil userUtil;
    private final UserRepository userRepository;

    @Transactional
    @Override
    public TransactionDto save(TransactionDto dto) {
        Integer userId;
        if (dto.getUserId() != null){
            userId= dto.getUserId();
        }else{
            userId= userUtil.getUserId();
        }

        return save(dto, userId);
    }

    @Transactional
    @Override
    public TransactionDto save(TransactionDto dto, Integer userId) {
        Transaction transaction = dtoToTransaction(dto, userId);

        Double currentBalance = transaction.getAccount().getBalance();

        if (transaction.getType().equals("Gasto")) {
            if (currentBalance < transaction.getAmount()) {
                throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, "La transacción excede el balance de la cuenta");
            }
            currentBalance -= transaction.getAmount();
        } else if (transaction.getType().equals("Ingreso")) {
            currentBalance += transaction.getAmount();
        } else {
            throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, "Tipo de transacción no válido");
        }

        transaction.getAccount().setBalance(currentBalance);
        return transactionToDto(transactionRepository.save(transaction));
    }

    @Override
    public TransactionDto edit(Integer transactionId, TransactionDto dto) {
        Integer userId = userUtil.getUserId();
        Transaction transaction = transactionRepository.findOneByIdAndUserId(transactionId, userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No existe la transaccion"));

        modelMapper.map(dto, transaction);
        return transactionToDto(transactionRepository.save(transaction));
    }

    @Override
    public List<TransactionDto> getAllByUser() {
        List<Transaction> transactions = transactionRepository.findAllByUserId(userUtil.getUserId(), Sort.by("id"));
        return transactions.stream().map(this::transactionToDto).toList();
    }

    private TransactionDto transactionToDto(Transaction transaction) {
        TransactionDto transactionDto = modelMapper.map(transaction, TransactionDto.class);
        transactionDto.setAccountId(transaction.getAccount().getId());
        transactionDto.setUserId(transaction.getUser().getId());
        return transactionDto;
    }

    private Transaction dtoToTransaction(TransactionDto dto, Integer userId) {
        Transaction transaction = modelMapper.map(dto, Transaction.class);
        transaction.setUser(userRepository.findById(userId).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "No existe el usuario")));
        transaction.setAccount(accountRepository.findOneByIdAndUserId(dto.getAccountId(), userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No existe la cuenta")));
        return transaction;
    }
}
