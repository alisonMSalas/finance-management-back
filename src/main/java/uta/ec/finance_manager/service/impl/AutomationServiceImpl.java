package uta.ec.finance_manager.service.impl;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import uta.ec.finance_manager.dto.AutomationDto;
import uta.ec.finance_manager.entity.Automation;
import uta.ec.finance_manager.repository.AccountRepository;
import uta.ec.finance_manager.repository.AutomationRepository;
import uta.ec.finance_manager.repository.UserRepository;
import uta.ec.finance_manager.service.AccountService;
import uta.ec.finance_manager.service.AutomationService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AutomationServiceImpl implements AutomationService {

    private final AutomationRepository automationRepository;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final AccountRepository accountRepository;

    @Override
    public AutomationDto create(AutomationDto automationDto) {
        return this.automationToDto(automationRepository.save(dtoToAutomation(automationDto)));
    }

    @Override
    public List<AutomationDto> getAutomationsByUser(Integer userId) {
        return this.automationRepository.findByUserId(userId).stream().map(this::automationToDto).toList();
    }

    @Override
    public AutomationDto edit(AutomationDto automationDto) {
        Automation automation = automationRepository.findById(automationDto.getId()).orElseThrow(
                ()-> new ResponseStatusException(HttpStatus.NOT_FOUND)
        );

        modelMapper.map(automationDto, automation);

        return automationToDto(automationRepository.save(automation));
    }

    @Override
    public void delete(Integer automationId) {
        Automation automation = automationRepository.findById(automationId).orElseThrow(
                ()-> new ResponseStatusException(HttpStatus.NOT_FOUND)
        );
        automationRepository.delete(automation);

    }

    private Automation dtoToAutomation(AutomationDto automationDto){
        Automation automation = this.modelMapper.map(automationDto, Automation.class);
        automation.setUser(userRepository.findById(automationDto.getUserId()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found")
        ));
        automation.setAccount(accountRepository.findById(automationDto.getAccountId()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND)
        ));
        return automation;
    }

    private  AutomationDto automationToDto(Automation automation){
        AutomationDto automationDto = this.modelMapper.map(automation, AutomationDto.class);
        automationDto.setUserId(automation.getUser().getId());
        automationDto.setAccountId(automation.getAccount().getId());
        return automationDto;
    }
}
