package uta.ec.finance_manager.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import uta.ec.finance_manager.dto.TransactionDto;
import uta.ec.finance_manager.entity.Account;
import uta.ec.finance_manager.entity.Budget;
import uta.ec.finance_manager.entity.Message;
import uta.ec.finance_manager.entity.Transaction;
import uta.ec.finance_manager.repository.AccountRepository;
import uta.ec.finance_manager.repository.BudgetRepository;
import uta.ec.finance_manager.repository.TransactionRepository;
import uta.ec.finance_manager.repository.UserRepository;
import uta.ec.finance_manager.service.BudgetService;
import uta.ec.finance_manager.service.MessageService;
import uta.ec.finance_manager.service.TransactionService;
import uta.ec.finance_manager.util.UserUtil;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;
    private final BudgetRepository budgetRepository;
    private final AccountRepository accountRepository;
    private final MessageService messageService;
    private final ModelMapper modelMapper;
    private final UserUtil userUtil;
    private final UserRepository userRepository;

    @Transactional
    @Override
    public TransactionDto save(TransactionDto dto) {
        Integer userId;
        if (dto.getUserId() != null) {
            userId = dto.getUserId();
        } else {
            userId = userUtil.getUserId();
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

        sendNotification(transaction);
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
        List<Transaction> transactions = transactionRepository.findAllByUserId(userUtil.getUserId(),
                Sort.by("date").descending());
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

    private void sendNotification(Transaction transaction) {
        List<Budget> budgets = budgetRepository.findByUserIdAndCategory(
                userUtil.getUserId(), transaction.getCategory());

        budgets.forEach(budget -> {
            // Obtener el monto actual y calcular el nuevo monto
            double anterior = budget.getCurrentAmount();
            double actual = anterior + transaction.getAmount();
            budget.setCurrentAmount(actual);
            budgetRepository.save(budget);

            // Calcular ratios
            double max = budget.getMaxAmount();
            double ratioAntes = max > 0 ? anterior / max : 0;
            double ratioAhora = max > 0 ? actual / max : 0;
            Integer userId = budget.getUser().getId();

            // Notificación al alcanzar el 75% del presupuesto
            if (ratioAntes < 0.75 && ratioAhora >= 0.75 && ratioAhora <= 1.0) {
                String contenido = "Has alcanzado el 75% del presupuesto para " + budget.getCategory();
                messageService.create("WARNING", contenido, userId);
                return;
            }

            // Notificación al exceder el presupuesto
            if (max <= actual) {
                String contenido = "Has excedido el presupuesto para " + budget.getCategory();
                messageService.create("ERROR", contenido, userId);
            }
        });
    }
}
