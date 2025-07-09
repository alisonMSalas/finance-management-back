package uta.ec.finance_manager.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import uta.ec.finance_manager.dto.AutomationDto;
import uta.ec.finance_manager.service.AutomationService;

import java.util.List;

@RestController
@RequestMapping("/automation")
@RequiredArgsConstructor
public class AutomationController {
    private final AutomationService automationService;

    @PostMapping
    public AutomationDto createAutomation(@Valid @RequestBody AutomationDto automationDto) {
        return this.automationService.create(automationDto);
    }

    @GetMapping
    public List<AutomationDto> getAutomationByUser() {
        return this.automationService.getAutomationsByUser();
    }

    @PutMapping
    public AutomationDto modifyAutomation(@Valid @RequestBody AutomationDto automationDto) {
        if (automationDto.getId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El ID de la automatización es requerido para la actualización");
        }
        return this.automationService.edit(automationDto);
    }

    @DeleteMapping("/{automationId}")
    public void deleteAutomation(@PathVariable Integer automationId) {
        this.automationService.delete(automationId);
    }
}