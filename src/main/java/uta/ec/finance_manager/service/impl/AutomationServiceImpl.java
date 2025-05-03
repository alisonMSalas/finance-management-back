package uta.ec.finance_manager.service.impl;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import uta.ec.finance_manager.dto.AutomationDto;
import uta.ec.finance_manager.dto.TransactionDto;
import uta.ec.finance_manager.entity.Account;
import uta.ec.finance_manager.entity.Automation;
import uta.ec.finance_manager.entity.User;
import uta.ec.finance_manager.enums.Frequency;
import uta.ec.finance_manager.repository.AccountRepository;
import uta.ec.finance_manager.repository.AutomationRepository;
import uta.ec.finance_manager.repository.UserRepository;
import uta.ec.finance_manager.service.AutomationService;
import uta.ec.finance_manager.service.MessageService;
import uta.ec.finance_manager.service.TransactionService;
import uta.ec.finance_manager.util.UserUtil;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AutomationServiceImpl implements AutomationService {

    private final AutomationRepository automationRepository;
    private final ModelMapper modelMapper;
    private final AccountRepository accountRepository;
    private final UserRepository userRepository;
    private final UserUtil userUtil;
    private final TransactionService transactionService;
    private final MessageService messageService;


    @Override
    @Transactional
    public AutomationDto create(AutomationDto automationDto) {
        Automation automation = dtoToAutomation(automationDto);
        return automationToDto(automationRepository.save(automation));
    }

    @Override
    public List<AutomationDto> getAutomationsByUser() {
        return automationRepository.findByUserId(userUtil.getUserId())
                .stream()
                .map(this::automationToDto)
                .toList();
    }

    @Override
    @Transactional
    public AutomationDto edit(AutomationDto automationDto) {
        Automation automation = automationRepository.findById(automationDto.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Automation with ID " + automationDto.getId() + " not found"));

        // Mapeo manual para evitar problemas con ModelMapper
        automation.setAmount(automationDto.getAmount());
        automation.setFrequency(automationDto.getFrequency());
        automation.setStartDate(automationDto.getStartDate());
        automation.setCategory(automationDto.getCategory());
        automation.setAccount(accountRepository.findById(automationDto.getAccountId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Account with ID " + automationDto.getAccountId() + " not found")));

        // Preservar el User existente
        if (automation.getUser() == null) {
            Integer userId = userUtil.getUserId();
            if (userId == null) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not authenticated");
            }
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                            "User with ID " + userId + " not found"));
            automation.setUser(user);
        }

        return automationToDto(automationRepository.save(automation));
    }

    @Override
    @Transactional
    public void delete(Integer automationId) {
        Automation automation = automationRepository.findById(automationId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Automation with ID " + automationId + " not found"));

        automationRepository.delete(automation);
    }

    @Transactional
    private void executeAutomation(Automation automation) {
        Account account = automation.getAccount();
        if (account == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "No account associated with automation ID " + automation.getId());
        }

        double amount = automation.getAmount();

        // Validar saldo suficiente si es gasto
        if (amount < 0 && account.getBalance() + amount < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Insufficient balance for automation ID " + automation.getId() +
                            ". Required: " + Math.abs(amount) + ", Available: " + account.getBalance());
        }

        // Crear DTO de transacción a partir de la automatización
        TransactionDto transactionDto = new TransactionDto();
        transactionDto.setAmount(Math.abs(amount));
        transactionDto.setAccountId(account.getId());
        transactionDto.setCategory(automation.getCategory());
        transactionDto.setDescription("Automatización ejecutada de: " + automation.getName());
        transactionDto.setType(amount > 0 ? "Ingreso" : "Gasto");
        transactionDto.setUserId(automation.getUser().getId());

        transactionService.save(transactionDto);

        automation.setLastExecutionDate(new Date());
        automationRepository.save(automation);
    }




    @Scheduled(cron = "0 0 0 * * ?")
    public void processAutomationsDaily() {
        LocalDate today = LocalDate.now();
        List<Automation> automations = automationRepository.findByStartDateBeforeWithAccount(
                Date.from(today.atStartOfDay(ZoneId.systemDefault()).toInstant())
        );


        for (Automation automation : automations) {

            if (shouldExecuteAutomation(automation)) {
                try {
                    executeAutomation(automation);
                    messageService.create("INFO", "Automatización ejecutada con éxito (" + automation.getName() + ")", automation.getUser().getId());
                } catch (Exception e) {
                    messageService.create("ERROR", "Falló la automatización (" + automation.getName() + "): Revise sus fondos!", automation.getUser().getId());
                }
            }
        }
    }


    private boolean shouldExecuteAutomation(Automation automation) {
        Frequency frequency = automation.getFrequency();
        Date lastExecution = automation.getLastExecutionDate() != null
                ? automation.getLastExecutionDate()
                : automation.getStartDate();

        if (lastExecution == null) {
            return frequency == Frequency.DAILY || frequency == Frequency.MONTHLY;
        }

        LocalDate lastExecutionDate = lastExecution.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
        LocalDate today = LocalDate.now();

        switch (frequency) {
            case DAILY:
                return true;
            case WEEKLY:
                return lastExecutionDate.plusWeeks(1).isEqual(today) ||
                        lastExecutionDate.plusWeeks(1).isBefore(today);
            case MONTHLY:
                // Ejecutar si la fecha de inicio es hoy o si no ha habido ejecución previa
                return lastExecutionDate.getMonth() != today.getMonth() ||
                        lastExecutionDate.getYear() != today.getYear() ||
                        automation.getStartDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().isEqual(today);
            default:
                return false;
        }
    }



    private Automation dtoToAutomation(AutomationDto automationDto) {
        if (automationDto.getAmount() == 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cantidad no puede ser cero");
        }

        if (automationDto.getStartDate() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Start date is required");
        }

        Automation automation = modelMapper.map(automationDto, Automation.class);
        Integer userId = userUtil.getUserId();
        if (userId == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not authenticated");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "User with ID " + userId + " not found"));
        automation.setUser(user);

        Account account = accountRepository.findById(automationDto.getAccountId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Account with ID " + automationDto.getAccountId() + " not found"));
        automation.setAccount(account);

        return automation;
    }

    private AutomationDto automationToDto(Automation automation) {
        AutomationDto automationDto = modelMapper.map(automation, AutomationDto.class);
        automationDto.setUserId(automation.getUser().getId());
        if (automation.getAccount() != null) {
            automationDto.setAccountId(automation.getAccount().getId());
        }
        return automationDto;
    }
}
