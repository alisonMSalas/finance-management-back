package uta.ec.finance_manager.service;

import uta.ec.finance_manager.dto.AutomationDto;
import uta.ec.finance_manager.entity.Automation;

import java.util.List;

public interface AutomationService {
    AutomationDto create(AutomationDto automationDto);

    List<AutomationDto> getAutomationsByUser(Integer userId);

    AutomationDto edit(AutomationDto automationDto);

    void delete(Integer automationId);
}
