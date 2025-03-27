package uta.ec.finance_manager.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uta.ec.finance_manager.dto.AutomationDto;
import uta.ec.finance_manager.entity.Automation;
import uta.ec.finance_manager.service.AutomationService;

import java.util.List;

@RestController
@RequestMapping("/automation")
@RequiredArgsConstructor
public class AutomationController {
    private final AutomationService automationService;
    @PostMapping()
    public AutomationDto createAutomation(@RequestBody AutomationDto automationDto){
        return this.automationService.create(automationDto);
    }

    @GetMapping()
    public List<AutomationDto> getAutomationByUser(@RequestParam Integer userId){
        return this.automationService.getAutomationsByUser(userId);
    }

    @PutMapping()
    public AutomationDto modifyAutomation(@RequestBody AutomationDto automationDto){
        return this.automationService.edit(automationDto);
    }

    @DeleteMapping()
    public void deleteAutomation(@RequestParam Integer automationId){
        this.automationService.delete(automationId);
    }

}
